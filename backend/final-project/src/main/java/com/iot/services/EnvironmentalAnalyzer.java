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
    private List<IAlgorithmStrategy> strategies;
    private List<IObserver> observers;
    private IMeasurementRepository repository;

    public EnvironmentalAnalyzer() {
        this.strategies = new ArrayList<>();
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

        //process the measurement using all defined algorithm strategies
        if (this.strategies != null) {
            for (IAlgorithmStrategy strategy : this.strategies) {
                strategy.process(m);
            }
        }

        //notify observers (like HardwareAlarmObserver or WebDashboardObserver)
        notifyObservers();
    }

    /**
     * @param strategies List of algorithm strategies.
     */
    public void setStrategies(List<IAlgorithmStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * @param repository The repository implementation.
     */
    public void setRepository(IMeasurementRepository repository) {
        this.repository = repository;
    }
}