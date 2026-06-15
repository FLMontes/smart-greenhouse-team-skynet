package com.iot.controllers;

import com.iot.models.dto.ActuatorStatus;
import com.iot.observers.HardwareAlarmObserver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorController {

    private final HardwareAlarmObserver hardwareObserver;

    public ActuatorController(HardwareAlarmObserver hardwareObserver) {
        this.hardwareObserver = hardwareObserver;
    }

    @GetMapping("/status")
    public ResponseEntity<ActuatorStatus> getActuatorStatus() {
        // Pedimos el DTO seguro que está guardado en el AtomicReference
        ActuatorStatus status = hardwareObserver.getLatestStatus();

        if (status.getBasedOnMeasurementId() == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(status);
    }
}