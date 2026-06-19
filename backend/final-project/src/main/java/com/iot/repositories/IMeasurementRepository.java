package com.iot.repositories;

import com.iot.models.entities.Measurement;
import java.util.List;

/**
 * Repository interface for Measurement data.
 */
public interface IMeasurementRepository {

    void save(Measurement m);

    List<Measurement> getHistory(int limit, int offset);

    Measurement getLatest();

    List<Measurement> getLatestWindow(int windowSize);
}
