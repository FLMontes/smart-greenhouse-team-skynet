package com.iot.services;

import com.iot.models.entities.Measurement;
import com.iot.observers.IObserver;
import com.iot.repositories.IMeasurementRepository;
import org.springframework.stereotype.Service;
import com.iot.strategies.IAlgorithmStrategy;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnvironmentalAnalyzer {

    private Measurement currentMeasurement;

    // Using 'final' ensures these are injected and never null
    private final List<IAlgorithmStrategy> strategies;
    private final List<IObserver> observers;
    private final IMeasurementRepository repository;

    // --- CONSTRUCTOR INJECTION ---
    // Spring Boot automatically finds the Repository and ALL your @Component Strategies
    // and injects them here perfectly assembled!
    public EnvironmentalAnalyzer(List<IAlgorithmStrategy> strategies, IMeasurementRepository repository) {
        this.strategies = strategies;
        this.repository = repository;
        this.observers = new ArrayList<>();
    }

    /**
     * @param o The observer to attach.
     */
    public void attach(IObserver o) {
        this.observers.add(o);
    }

    /**
     * @param o The observer to detach.
     */
    public void detach(IObserver o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        for (IObserver observer : this.observers) {
            observer.update();
        }
    }

    /**
     * @param m The measurement to analyze.
     */
    public void analyzeMeasurement(Measurement m) {
        this.currentMeasurement = m;

        // Process the measurement using all defined algorithm strategies
        if (this.strategies != null) {
            for (IAlgorithmStrategy strategy : this.strategies) {
                strategy.process(m);
            }
        }

        // Notify observers (like HardwareAlarmObserver)
        notifyObservers();
    }

    public Measurement getCurrentMeasurement() {
        return this.currentMeasurement;
    }
}