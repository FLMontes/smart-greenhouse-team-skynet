package com.iot.observers;

import com.iot.models.dto.ActuatorStatus;
import com.iot.models.dto.AlgorithmResult;
import com.iot.models.entities.Measurement;
import com.iot.services.EnvironmentalAnalyzer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class HardwareAlarmObserver implements IObserver {

    private final EnvironmentalAnalyzer analyzer;

    // NUEVO: La "caja fuerte" que guarda el último estado de los actuadores de forma segura.
    private final AtomicReference<ActuatorStatus> currentStatus = new AtomicReference<>(new ActuatorStatus());

    public HardwareAlarmObserver(EnvironmentalAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.analyzer.attach(this);
    }

    @Override
    public void update() {
        Measurement m = analyzer.getCurrentMeasurement();
        if (m == null) return;

        // 1. Creamos un objeto de estado LOCAL (100% seguro contra concurrencia)
        ActuatorStatus newStatus = new ActuatorStatus();
        newStatus.setBasedOnMeasurementId(m.getId());
        newStatus.setTimestamp(LocalDateTime.now());
        newStatus.setRgbColorCommand("#00FF00"); // Verde por defecto

        // Lógica de Silencio (Mute)
        boolean isMuted = m.getButtonPressed() != null && m.getButtonPressed();
        // 2. Le pedimos al Analizador los resultados procesados
        List<AlgorithmResult> results = analyzer.getLatestAlgorithmResults();

        // 3. Calculamos qué encender basados en esos resultados
        for (AlgorithmResult res : results) {
            switch (res.getAlgorithm()) {
                case "TemperatureStrategy":
                    if (res.getValue() == 1.0f) { // Too Hot
                        newStatus.setRgbColorCommand("#FF0000"); // RED
                        newStatus.setFanStatus(true);
                        if (!isMuted) newStatus.setBuzzerStatus(true);
                    } else if (res.getValue() == -1.0f) { // Too Cold
                        newStatus.setRgbColorCommand("#0000FF"); // BLUE
                        newStatus.setResistorStatus(true);
                        if (!isMuted) newStatus.setBuzzerStatus(true);
                    }
                    break;
                case "HumidityStrategy":
                    if (res.getValue() == 1.0f) { // Watering required
                        if (newStatus.getRgbColorCommand().equals("#00FF00")) newStatus.setRgbColorCommand("#FFFFFF"); // WHITE
                        newStatus.setMotorStatus(true);
                    }
                    break;
                case "CO2Strategy":
                    if (res.getValue() == 1.0f) { // Ventilation required
                        if (newStatus.getRgbColorCommand().equals("#00FF00")) newStatus.setRgbColorCommand("#FFC0CB"); // PINK
                        newStatus.setFanStatus(true);
                        if (!isMuted) newStatus.setBuzzerStatus(true);
                    }
                    break;
                case "LightStrategy":
                    if (res.getValue() > 0) { // Light deficit
                        if (newStatus.getRgbColorCommand().equals("#00FF00")) newStatus.setRgbColorCommand("#FFFF00"); // YELLOW
                        newStatus.setLedIntensityCommand(80);
                    }
                    break;
            }
        }

        // 4. Guardamos el resultado final en la caja fuerte atómica
        this.currentStatus.set(newStatus);
    }

    // NUEVO: Método seguro para que el controlador lea el estado
    public ActuatorStatus getLatestStatus() {
        return this.currentStatus.get();
    }
}