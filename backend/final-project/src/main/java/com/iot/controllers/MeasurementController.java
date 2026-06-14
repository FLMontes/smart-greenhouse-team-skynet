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
@RequestMapping("/api")
public class MeasurementController {

    private final EnvironmentalAnalyzer analyzer;
    private final IMeasurementRepository repository;

    // Spring Boot automatically injects the dependencies
    public MeasurementController(EnvironmentalAnalyzer analyzer, IMeasurementRepository repository) {
        this.analyzer = analyzer;
        this.repository = repository;
    }

    // Fulfills the POST /api/measurements from the OpenAPI contract
    @PostMapping("/measurements")
    public ResponseEntity<Measurement> receiveMeasurement(@Valid @RequestBody Measurement m) {
        // Add the timestamp before saving
        m.setTimestamp(LocalDateTime.now());

        // 1. Persist to the PostgreSQL database
        repository.save(m);

        // 2. Pass the measurement through the analyzer (algorithms and observers)
        analyzer.analyzeMeasurement(m);

        // 3. Respond with 201 Created according to the contract
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    // Fulfills the GET /api/measurements from the OpenAPI contract
    @GetMapping("/measurements")
    public ResponseEntity<List<Measurement>> getHistory() {
        return ResponseEntity.ok(repository.getHistory());
    }
}
