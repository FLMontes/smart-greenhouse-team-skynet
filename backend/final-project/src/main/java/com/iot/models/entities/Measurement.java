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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensor_readings")

public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = -10, message = "Temperature cannot be less than -10")
    @Max(value = 60, message = "Temperature cannot be greater than 60")
    private Float temperature;

    @Min(value = 0, message = "Humidity cannot be less than 0")
    @Max(value = 100, message = "Humidity cannot be greater than 100")
    private Float humidity;

    @Min(value = 0, message = "Light cannot be less than 0")
    @Max(value = 100000, message = "Light cannot be greater than 100000")
    private Float light;

    @Min(value = 0, message = "CO2 cannot be less than 0")
    @Max(value = 5000, message = "CO2 cannot be greater than 5000")
    private Float co2;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    @Column(name = "button_pressed")
    private Boolean buttonPressed;
}