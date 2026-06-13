package com.iot.repositories;
import com.iot.models.entities.Measurement;
import java.util.List;

/**
 * Repository interface for Measurement data.
 * Defines the contract for saving and retrieving historical environmental data.
 */
public interface IMeasurementRepository {

    /**
     * Saves a new measurement to the database.
     * @param m The measurement to save.
     */
    void save(Measurement m);

    /**
     * Retrieves the history of measurements.
     * @return A list of stored measurements.
     */
    List<Measurement> getHistory();
}