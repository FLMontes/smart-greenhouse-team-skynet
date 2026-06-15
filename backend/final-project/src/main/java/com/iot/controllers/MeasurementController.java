package com.iot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.models.dto.ErrorResponse;
import com.iot.models.entities.Measurement;
import com.iot.repositories.IMeasurementRepository;
import com.iot.services.EnvironmentalAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    private final EnvironmentalAnalyzer analyzer;
    private final IMeasurementRepository repository;

    public MeasurementController(
            EnvironmentalAnalyzer analyzer,
            IMeasurementRepository repository) {
        this.analyzer = analyzer;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> receiveMeasurement(@RequestBody String payload) {
        if (!validatePayload(payload)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "INVALID_REQUEST",
                            "Invalid request payload.",
                            LocalDateTime.now().toString()
                    ));
        }

        Measurement m;
        try {
            m = parseMeasurement(payload);
        } catch (IOException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "INVALID_REQUEST",
                            "Invalid request payload.",
                            LocalDateTime.now().toString()
                    ));
        }

        m.setTimestamp(LocalDateTime.now());
        repository.save(m);
        analyzer.analyzeMeasurement(m);

        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @GetMapping
    public ResponseEntity<List<Measurement>> getHistory(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        int safeOffset = Math.max(0, offset);
        int safeLimit = Math.min(1000, Math.max(1, limit));

        // SOLUCIÓN ERROR 1: Le pasamos limit y offset al repositorio
        List<Measurement> history = repository.getHistory(safeLimit, safeOffset);

        return ResponseEntity.ok(history);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMeasurement() {

        // SOLUCIÓN ERROR 2: Usamos el método que creamos para traer solo la última
        Measurement latest = repository.getLatest();

        if (latest == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(
                            "NOT_FOUND",
                            "No measurements found.",
                            LocalDateTime.now().toString()
                    ));
        }
        return ResponseEntity.ok(latest);
    }

    private boolean validatePayload(String payload) {
        try {
            Measurement measurement = parseMeasurement(payload);
            return measurement.getTemperature() != null
                    && measurement.getTemperature() >= -10
                    && measurement.getTemperature() <= 60
                    && measurement.getHumidity() != null
                    && measurement.getHumidity() >= 0
                    && measurement.getHumidity() <= 100
                    && measurement.getLight() != null
                    && measurement.getLight() >= 0
                    && measurement.getLight() <= 100000
                    && measurement.getCo2() != null
                    && measurement.getCo2() >= 0
                    && measurement.getCo2() <= 5000
                    && measurement.isButtonPressed() != null;
        } catch (IOException ex) {
            return false;
        }
    }

    private Measurement parseMeasurement(String payload) throws IOException {
        return new ObjectMapper().readValue(payload, Measurement.class);
    }
}