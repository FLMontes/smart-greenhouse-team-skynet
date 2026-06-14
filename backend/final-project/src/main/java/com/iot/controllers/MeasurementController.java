package com.iot.controllers;

import com.iot.models.dto.MeasurementInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //Esta linea establece el endPoint es propia de SpringBoot
@RequestMapping("/api/measurements")
public class MeasurementController {

    @PostMapping
    public ResponseEntity<String> createMeasurement(@Valid @RequestBody MeasurementInput payload) {
        
        // Aquí es donde en el futuro llamaremos a la base de datos para guardar.
        // Por ahora, solo imprimimos en consola para verificar que llega bien.
        System.out.println("Lectura válida recibida desde ESP32:");
        System.out.println("Temperatura: " + payload.getTemperature());
        System.out.println("Humedad: " + payload.getHumidity());

        // Devolvemos el código 201 (Created) como pide la documentación OpenAPI
        return ResponseEntity.status(HttpStatus.CREATED).body("Measurement received and validated successfully.");
    }
}