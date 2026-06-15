package com.iot.controllers;

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

    // Constructor Injection (¡La best practice que te elogiaron!)
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

        // Hora del servidor en UTC
        m.setTimestamp(LocalDateTime.now());

        // Guardamos en la base y analizamos (T26)
        repository.save(m);
        analyzer.analyzeMeasurement(m);

        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @GetMapping
    public ResponseEntity<List<Measurement>> getHistory() {
        return ResponseEntity.ok(repository.getHistory());
    }
}