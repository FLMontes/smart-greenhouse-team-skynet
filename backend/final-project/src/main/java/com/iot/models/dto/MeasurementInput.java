package com.iot.models.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeasurementInput {
    private Long sensorId;

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "-10.0", message = "La temperatura no puede ser menor a -10")
    @DecimalMax(value = "60.0", message = "La temperatura no puede ser mayor a 60")
    private Float temperature;

    @NotNull(message = "La humedad es obligatoria")
    @DecimalMin(value = "0.0", message = "La humedad no puede ser menor a 0")
    @DecimalMax(value = "100.0", message = "La humedad no puede ser mayor a 100")
    private Float humidity;

    @NotNull(message = "La luz es obligatoria")
    @DecimalMin(value = "0.0", message = "La luz no puede ser menor a 0")
    @DecimalMax(value = "100000.0", message = "La luz no puede ser mayor a 100000")
    private Float light;

    @NotNull(message = "El CO2 es obligatorio")
    @DecimalMin(value = "0.0", message = "El CO2 no puede ser menor a 0")
    @DecimalMax(value = "5000.0", message = "El CO2 no puede ser mayor a 5000")
    private Float co2;

    @NotNull(message = "El estado del botón es obligatorio")
    private Boolean buttonPressed;
}