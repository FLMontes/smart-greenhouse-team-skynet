package com.iot.repositories;

import com.iot.models.entities.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA access for {@link SensorReading}.
 *
 * <p>Inherited {@code save}, {@code findAll}, etc. Custom queries (e.g. {@code findBySensorId}) can be
 * added for coursework.
 */
public interface SensorsRepository extends JpaRepository<SensorReading, Integer> {}
