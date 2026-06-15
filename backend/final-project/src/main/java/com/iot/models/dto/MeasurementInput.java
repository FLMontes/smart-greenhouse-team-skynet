package com.iot.models.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MeasurementInput {

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "-5.0", message = "La temperatura no puede ser menor a -5")
    @DecimalMax(value = "50.0", message = "La temperatura no puede ser mayor a 50")
    private Float temperature;

    @NotNull(message = "La humedad es obligatoria")
    @DecimalMin(value = "0.0", message = "La humedad no puede ser menor a 0")
    @DecimalMax(value = "100.0", message = "La humedad no puede ser mayor a 100")
    private Float humidity;

    @NotNull(message = "La luz es obligatoria")
    @DecimalMin(value = "0.0", message = "La luz no puede ser menor a 0")
    @DecimalMax(value = "20000.0", message = "La luz no puede ser mayor a 20000")
    private Float light;

    @NotNull(message = "El CO2 es obligatorio")
    @DecimalMin(value = "300.0", message = "El CO2 no puede ser menor a 300")
    @DecimalMax(value = "2000.0", message = "El CO2 no puede ser mayor a 2000")
    private Float co2;

    @NotNull(message = "El estado del botón es obligatorio")
    private Boolean buttonPressed;

    // Getters y Setters
    public Float getTemperature() { return temperature; }
    public void setTemperature(Float temperature) { this.temperature = temperature; }

    public Float getHumidity() { return humidity; }
    public void setHumidity(Float humidity) { this.humidity = humidity; }

    public Float getLight() { return light; }
    public void setLight(Float light) { this.light = light; }

    public Float getCo2() { return co2; }
    public void setCo2(Float co2) { this.co2 = co2; }

    public Boolean getButtonPressed() { return buttonPressed; }
    public void setButtonPressed(Boolean buttonPressed) { this.buttonPressed = buttonPressed; }
}