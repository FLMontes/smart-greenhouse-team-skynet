package com.iot.controllers;


import com.iot.models.dto.ActuatorStatus;
import com.iot.models.entities.Measurement;
import com.iot.observers.HardwareAlarmObserver;
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


    // NEW: Injecting the observer to read the hardware status
    private final HardwareAlarmObserver hardwareObserver;


    // Constructor-based dependency injection
    public MeasurementController(
            EnvironmentalAnalyzer analyzer,
            IMeasurementRepository repository,
            HardwareAlarmObserver hardwareObserver) {
        this.analyzer = analyzer;
        this.repository = repository;
        this.hardwareObserver = hardwareObserver;
    }


    // Fulfills the POST /api/measurements endpoint from the OpenAPI contract
    @PostMapping
    public ResponseEntity<Measurement> receiveMeasurement(@Valid @RequestBody Measurement m) {
        m.setTimestamp(LocalDateTime.now());


        // Persist the new measurement to the database
        repository.save(m);


        // Process the measurement through the configured algorithms and notify observers
        analyzer.analyzeMeasurement(m);


        // Return HTTP 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }


    // Fulfills the GET /api/measurements endpoint from the OpenAPI contract
    @GetMapping
    public ResponseEntity<List<Measurement>> getHistory() {
        return ResponseEntity.ok(repository.getHistory());
    }


    // NEW: Endpoint for the WebDashboardObserver to fetch the latest data
    @GetMapping("/latest")
    public ResponseEntity<Measurement> getLatestMeasurement() {
        Measurement m = analyzer.getCurrentMeasurement();
        if (m == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(m);
    }


    // NEW: Endpoint to expose the hardware actuators status.
    // This will solve all the "never used" yellow warnings in ActuatorStatus!
    @GetMapping("/actuators/status")
    public ResponseEntity<ActuatorStatus> getActuatorStatus() {
        ActuatorStatus status = new ActuatorStatus();


        // We use the setters here. Spring Boot (Jackson) will automatically
        // use the getters when converting this object to JSON!
        status.setBasedOnMeasurementId(hardwareObserver.getBasedOnMeasurementId());
        status.setTimestamp(hardwareObserver.getTimestamp());
        status.setFanStatus(hardwareObserver.isFanStatus());
        status.setBuzzerStatus(hardwareObserver.isBuzzerStatus());
        status.setMotorStatus(hardwareObserver.isMotorStatus());
        status.setResistorStatus(hardwareObserver.isResistorStatus());
        status.setAlarmMuted(hardwareObserver.isAlarmMuted());
        status.setRgbColorCommand(hardwareObserver.getRgbColorCommand());
        status.setLedIntensityCommand(hardwareObserver.getLedIntensityCommand());


        return ResponseEntity.ok(status);
    }
}
