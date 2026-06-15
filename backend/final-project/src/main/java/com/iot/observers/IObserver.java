package com.iot.observers;
/**
 * Interface for the Observer pattern.
 * Defines the contract for all observers that need to react to environmental changes.
 */
public interface IObserver {

    /**
     * Method called by the Subject (EnvironmentalAnalyzer) to notify the observer
     * that a new measurement has been processed and updates are available.
     */
    void update();
}