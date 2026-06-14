package com.iot.models.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MeasurementInput {

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "-10.0", message = "La temperatura no puede ser menor a -10")
    @DecimalMax(value = "60.0", message = "La temperatura no puede ser mayor a 60")
    private Double temperature;

    @NotNull(message = "La humedad es obligatoria")
    @DecimalMin(value = "0.0", message = "La humedad no puede ser menor a 0")
    @DecimalMax(value = "100.0", message = "La humedad no puede ser mayor a 100")
    private Double humidity;

    @NotNull(message = "La luz es obligatoria")
    @DecimalMin(value = "0.0", message = "La luz no puede ser menor a 0")
    @DecimalMax(value = "100000.0", message = "La luz no puede ser mayor a 100000")
    private Double light;

    @NotNull(message = "El CO2 es obligatorio")
    @DecimalMin(value = "0.0", message = "El CO2 no puede ser menor a 0")
    @DecimalMax(value = "5000.0", message = "El CO2 no puede ser mayor a 5000")
    private Double co2;

    @NotNull(message = "El estado del botón es obligatorio")
    private Boolean buttonPressed;

    // Getters y Setters
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Double getLight() { return light; }
    public void setLight(Double light) { this.light = light; }

    public Double getCo2() { return co2; }
    public void setCo2(Double co2) { this.co2 = co2; }

    public Boolean getButtonPressed() { return buttonPressed; }
    public void setButtonPressed(Boolean buttonPressed) { this.buttonPressed = buttonPressed; }
}