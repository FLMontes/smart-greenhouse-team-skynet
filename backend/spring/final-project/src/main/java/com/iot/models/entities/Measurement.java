package com.iot.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entity class representing an environmental measurement from the ESP32.
 * Contains data for temperature, humidity, light, CO2, and the physical button.
 */
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Required by Spring Boot/JPA for the database table

    private float temperature;
    private float humidity;
    private float light;
    private float co2;
    private LocalDateTime timestamp;
    private boolean buttonPressed;

    // --- Getters ---
    public Long getId() { return id; }
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
    public float getLight() { return light; }
    public float getCo2() { return co2; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isButtonPressed() { return buttonPressed; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
    public void setHumidity(float humidity) { this.humidity = humidity; }
    public void setLight(float light) { this.light = light; }
    public void setCo2(float co2) { this.co2 = co2; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setButtonPressed(boolean buttonPressed) { this.buttonPressed = buttonPressed; }
}