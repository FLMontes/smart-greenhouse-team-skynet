package com.iot.strategies;
/**
 * Interface for the Strategy pattern.
 * Defines the contract for all algorithms that process environmental measurements.
 */
public interface IAlgorithmStrategy {

    /**
     * Processes a measurement to evaluate specific environmental conditions
     * (like temperature, humidity, CO2, etc.).
     *
     * @param m The measurement object to be processed.
     */
    void process(Measurement m);
}