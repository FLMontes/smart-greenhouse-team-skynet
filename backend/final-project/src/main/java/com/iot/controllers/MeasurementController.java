package com.iot.controllers;

import com.iot.models.entities.Measurement;
import com.iot.repositories.IMeasurementRepository;
import com.iot.services.EnvironmentalAnalyzer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    private final EnvironmentalAnalyzer analyzer;
    private final IMeasurementRepository repository;

    // Constructor-based dependency injection
    public MeasurementController(EnvironmentalAnalyzer analyzer, IMeasurementRepository repository) {
        this.analyzer = analyzer;
        this.repository = repository;
    }

    // Fulfills the POST /api/measurements endpoint from the OpenAPI contract
    @PostMapping
    public ResponseEntity<Measurement> receiveMeasurement(@Valid @RequestBody Measurement m) {
        // Set the current timestamp before persisting the data
        m.setTimestamp(LocalDateTime.now());

        // Persist the new measurement to the PostgreSQL database
        repository.save(m);

        // Process the measurement through the configured algorithms and notify observers
        analyzer.analyzeMeasurement(m);

        // Return HTTP 201 Created with the persisted measurement payload
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    // Fulfills the GET /api/measurements endpoint from the OpenAPI contract
    @GetMapping
    public ResponseEntity<List<Measurement>> getHistory() {
        // Retrieve and return the historical measurements from the database
        return ResponseEntity.ok(repository.getHistory());
    }
}