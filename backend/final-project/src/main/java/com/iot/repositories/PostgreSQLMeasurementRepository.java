package com.iot.repositories;

import com.iot.models.entities.Measurement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Concrete implementation for PostgreSQL based on the Class Diagram.
 * The @Repository annotation allows Spring Boot to manage this bean.
 */
@Repository
public class PostgreSQLMeasurementRepository implements IMeasurementRepository {

    // Attributes defined in the class diagram
    private String connectionString;
    private boolean isConnected;

    // Native Spring/JPA tool to handle database operations
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructor: Spring Boot automatically injects the connection URL
     * from the application properties/.env file.
     */
    public PostgreSQLMeasurementRepository(@Value("${spring.datasource.url}") String connectionString) {
        this.connectionString = connectionString;
        this.isConnected = false;
    }

    public void connect() {
        // Spring Boot manages the actual connection, but we fulfill the diagram's logic
        this.isConnected = true;
        System.out.println("Connecting to PostgreSQL at: " + this.connectionString);
    }

    public void disconnect() {
        this.isConnected = false;
        System.out.println("Disconnecting from PostgreSQL...");
    }

    @Override
    @Transactional
    public void save(Measurement m) {
        connect();
        // Persist the measurement in the database
        entityManager.persist(m);
        disconnect();
    }

    @Override
    public List<Measurement> getHistory() {
        connect();
        // Query to fetch the latest 1000 records, ordered by timestamp descending
        // to comply with RNF-06 and RNF-15.
        List<Measurement> history = entityManager
                .createQuery("SELECT m FROM Measurement m ORDER BY m.timestamp DESC", Measurement.class)
                .setMaxResults(1000)
                .getResultList();
        disconnect();

        return history;
    }
}