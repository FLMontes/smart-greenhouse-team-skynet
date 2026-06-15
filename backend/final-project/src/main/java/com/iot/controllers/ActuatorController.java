package com.iot.controllers;

import com.iot.models.dto.ActuatorStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorController {

    // Endpoint: GET /api/actuators/status
    @GetMapping("/status")
    public ResponseEntity<ActuatorStatus> getStatus() {
        // Por ahora devolvemos un estado mock/hardcodeado simulando el estado del invernadero.
        ActuatorStatus mockStatus = new ActuatorStatus(
                false,     // fanStatus
                true,      // buzzerStatus
                true,      // motorStatus
                false,     // resistorStatus
                "#00FF00", // rgbColorCommand
                70         // ledIntensityCommand
        );

        return ResponseEntity.ok(mockStatus);
    }
}