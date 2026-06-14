package com.iot.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This forces Hibernate to accept the 'int4' (integer) column from your database
    // while keeping the Java type as Long (int64) to strictly respect the OpenAPI contract.
    @Column(columnDefinition = "integer")
    private Long id;

    // Validations based strictly on the OpenAPI contract
    @Min(value = -5, message = "Temperature cannot be less than -5")
    @Max(value = 50, message = "Temperature cannot be greater than 50")
    private Float temperature;

    @Min(value = 0, message = "Humidity cannot be less than 0")
    @Max(value = 100, message = "Humidity cannot be greater than 100")
    private Float humidity;

    @Min(value = 0, message = "Light cannot be less than 0")
    @Max(value = 20000, message = "Light cannot be greater than 20000")
    private Float light;

    @Min(value = 300, message = "CO2 cannot be less than 300")
    @Max(value = 2000, message = "CO2 cannot be greater than 2000")
    private Float co2;

    private LocalDateTime timestamp;

    // Ensure the column name matches the database snake_case format
    @Column(name = "button_pressed")
    private Boolean buttonPressed;

    // --- GETTERS ---
    // We MUST include getId() so Spring Boot (Jackson) can include the ID in the JSON response
    // as mandated by the OpenAPI contract.
    public Long getId() { return id; }
    public Float getTemperature() { return temperature; }
    public Float getHumidity() { return humidity; }
    public Float getLight() { return light; }
    public Float getCo2() { return co2; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Boolean isButtonPressed() { return buttonPressed; }

    // --- SETTERS ---
    public void setId(Long id) { this.id = id; }
    public void setTemperature(Float temperature) { this.temperature = temperature; }
    public void setHumidity(Float humidity) { this.humidity = humidity; }
    public void setLight(Float light) { this.light = light; }
    public void setCo2(Float co2) { this.co2 = co2; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setButtonPressed(Boolean buttonPressed) { this.buttonPressed = buttonPressed; }
}