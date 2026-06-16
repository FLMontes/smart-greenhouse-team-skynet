package com.iot.services;

import com.iot.models.dto.AlgorithmResult;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.Alert;
import com.iot.models.entities.Measurement;
import com.iot.observers.IObserver;
import com.iot.repositories.IMeasurementRepository;
import com.iot.strategies.IAlgorithmStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EnvironmentalAnalyzer {

    private final List<IAlgorithmStrategy> strategies;
    private final List<IObserver> observers;
    private final IMeasurementRepository repository;

    // --- ESTADO CENTRALIZADO ---
    private Measurement currentMeasurement;
    private Measurement currentAveragedMeasurement; // <-- NUEVO: Guardamos el dato promediado
    private List<AlgorithmResult> latestAlgorithmResults = new ArrayList<>();

    // NUEVO: Lista de alertas activas y un generador de IDs (Punto 6)
    private List<Alert> activeAlerts = new ArrayList<>();
    private final AtomicLong alertIdCounter = new AtomicLong(1);

    public EnvironmentalAnalyzer(List<IAlgorithmStrategy> strategies, IMeasurementRepository repository) {
        this.strategies = strategies;
        this.repository = repository;
        this.observers = new ArrayList<>();
    }

    public void attach(IObserver o) { this.observers.add(o); }
    public void detach(IObserver o) { this.observers.remove(o); }

    public void notifyObservers() {
        for (IObserver observer : this.observers) {
            observer.update();
        }
    }

    // APLICADO: 'synchronized' asegura que el ciclo completo sea atómico (hilo seguro)
    public synchronized void analyzeMeasurement(Measurement m) {
        this.currentMeasurement = m;

        // 1. Obtener ventana histórica
        List<Measurement> window = repository.getLatestWindow(5);
        if (window.isEmpty()) window.add(m);

        // 2. Construir la medición promediada
        Measurement averaged = buildAveragedMeasurement(window);
        this.currentAveragedMeasurement = averaged; // <-- NUEVO: Lo guardamos de forma segura

        // 3. Crear el Contexto de Análisis
        AnalysisContext context = new AnalysisContext(averaged, window, m.getId(), LocalDateTime.now());

        // 4. Ejecutar estrategias
        List<AlgorithmResult> results = new ArrayList<>();
        if (this.strategies != null) {
            for (IAlgorithmStrategy strategy : this.strategies) {
                results.add(strategy.process(context));
            }
        }

        this.latestAlgorithmResults = results;

        // 5. NUEVO: Generar las alertas activas basadas en los resultados
        generateAlerts(results, m.getId());

        // 6. Notificar a los observadores
        notifyObservers();
    }

    // --- MÉTODO PARA GENERAR ALERTAS LÓGICAS ---
    private void generateAlerts(List<AlgorithmResult> results, Integer measurementId) {
        List<Alert> newAlerts = new ArrayList<>();

        for (AlgorithmResult res : results) {
            if (res.getAlgorithm().equals("TemperatureStrategy")) {
                if (res.getValue() == 1.0f) {
                    newAlerts.add(new Alert(alertIdCounter.getAndIncrement(), "HIGH_TEMPERATURE", "Temperature exceeded maximum threshold.", "HIGH", true, measurementId, LocalDateTime.now()));
                } else if (res.getValue() == -1.0f) {
                    newAlerts.add(new Alert(alertIdCounter.getAndIncrement(), "LOW_TEMPERATURE", "Temperature dropped below minimum threshold.", "MEDIUM", true, measurementId, LocalDateTime.now()));
                }
            } else if (res.getAlgorithm().equals("CO2Strategy") && res.getValue() == 1.0f) {
                newAlerts.add(new Alert(alertIdCounter.getAndIncrement(), "HIGH_CO2", "CO2 levels require ventilation.", "HIGH", true, measurementId, LocalDateTime.now()));
            } else if (res.getAlgorithm().equals("HumidityStrategy") && res.getValue() == 1.0f) {
                newAlerts.add(new Alert(alertIdCounter.getAndIncrement(), "LOW_HUMIDITY", "Watering is required.", "LOW", true, measurementId, LocalDateTime.now()));
            } else if (res.getAlgorithm().equals("LightStrategy") && res.getValue() > 0) {
                newAlerts.add(new Alert(alertIdCounter.getAndIncrement(), "LOW_LIGHT", "Light deficit detected.", "LOW", true, measurementId, LocalDateTime.now()));
            }
        }

        this.activeAlerts = newAlerts;
    }

    private Measurement buildAveragedMeasurement(List<Measurement> window) {
        Measurement avg = new Measurement();
        float tempSum = 0, humSum = 0, lightSum = 0, co2Sum = 0;

        for (Measurement w : window) {
            tempSum += w.getTemperature();
            humSum += w.getHumidity();
            lightSum += w.getLight();
            co2Sum += w.getCo2();
        }

        int size = window.size();
        avg.setTemperature(tempSum / size);
        avg.setHumidity(humSum / size);
        avg.setLight(lightSum / size);
        avg.setCo2(co2Sum / size);
        return avg;
    }

    // --- GETTERS ---
    public Measurement getCurrentMeasurement() { return currentMeasurement; }
    public Measurement getCurrentAveragedMeasurement() { return currentAveragedMeasurement; } // <-- NUEVO GETTER
    public List<AlgorithmResult> getLatestAlgorithmResults() { return latestAlgorithmResults; }
    public List<Alert> getActiveAlerts() { return activeAlerts; }
}
