package com.iot.repositories;

import com.iot.models.entities.Measurement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class PostgreSQLMeasurementRepository implements IMeasurementRepository {

    private String connectionString;
    private boolean isConnected;

    @PersistenceContext
    private EntityManager entityManager;

    public PostgreSQLMeasurementRepository(@Value("${spring.datasource.url}") String connectionString) {
        this.connectionString = connectionString;
        this.isConnected = false;
    }

    public void connect() { this.isConnected = true; }
    public void disconnect() { this.isConnected = false; }

    @Override
    @Transactional
    public void save(Measurement m) {
        connect();
        entityManager.persist(m);
        disconnect();
    }

    @Override
    public List<Measurement> getHistory(int limit, int offset) {
        connect();
        List<Measurement> history = entityManager
                .createQuery("SELECT m FROM Measurement m ORDER BY m.timestamp DESC", Measurement.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        disconnect();
        return history;
    }

    @Override
    public Measurement getLatest() {
        connect();
        List<Measurement> results = entityManager
                .createQuery("SELECT m FROM Measurement m ORDER BY m.timestamp DESC", Measurement.class)
                .setMaxResults(1)
                .getResultList();
        disconnect();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Measurement> getLatestWindow(int windowSize) {
        connect();
        List<Measurement> window = entityManager
                .createQuery("SELECT m FROM Measurement m ORDER BY m.timestamp DESC", Measurement.class)
                .setMaxResults(windowSize)
                .getResultList();
        disconnect();
        return window;
    }
}