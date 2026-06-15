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
        ActuatorStatus status = new ActuatorStatus();

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
