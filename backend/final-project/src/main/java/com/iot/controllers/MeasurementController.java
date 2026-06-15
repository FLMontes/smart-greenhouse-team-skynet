package com.iot.controllers;

import com.iot.models.dto.ErrorResponse;
import com.iot.models.dto.MeasurementInput;
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
    private final HardwareAlarmObserver hardwareObserver;

    public MeasurementController(EnvironmentalAnalyzer analyzer, IMeasurementRepository repository) {
        this.analyzer = analyzer;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Measurement> receiveMeasurement(@Valid @RequestBody MeasurementInput input) {
        Measurement m = new Measurement();

        // Mapeo manual limpio
        m.setTemperature(input.getTemperature());
        m.setHumidity(input.getHumidity());
        m.setLight(input.getLight());
        m.setCo2(input.getCo2());
        m.setButtonPressed(input.getButtonPressed());

        // Timestamp del servidor
        m.setTimestamp(LocalDateTime.now());

        // Guardamos y analizamos
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

        List<Measurement> history = repository.getHistory(safeLimit, safeOffset);

        return ResponseEntity.ok(history);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMeasurement() {

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
}