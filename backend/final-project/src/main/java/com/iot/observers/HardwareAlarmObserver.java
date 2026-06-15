package com.iot.observers;

import com.iot.models.entities.Measurement;
import com.iot.services.EnvironmentalAnalyzer;
import com.iot.strategies.CO2Strategy;
import com.iot.strategies.HumidityStrategy;
import com.iot.strategies.LightStrategy;
import com.iot.strategies.TemperatureStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HardwareAlarmObserver implements IObserver {

    // Attributes defined in the class diagram
    private boolean fanStatus;
    private boolean alarmEnabled = true; // By default, the alarm is enabled
    private boolean buzzerStatus;
    private boolean motorStatus; // Watering motor
    private boolean resistorStatus; // Heating resistor
    private String rgbColorCommand = "#00FF00"; // GREEN (Normal state)
    private int ledIntensityCommand;

    // Attributes strictly required by the OpenAPI contract
    private Integer basedOnMeasurementId;
    private LocalDateTime timestamp;

    // References to the subject and the specific strategies
    private final EnvironmentalAnalyzer analyzer;
    private final TemperatureStrategy tempStrategy;
    private final HumidityStrategy humStrategy;
    private final CO2Strategy co2Strategy;
    private final LightStrategy lightStrategy;

    // Constructor: Inject everything we need to make physical decisions
    public HardwareAlarmObserver(
            EnvironmentalAnalyzer analyzer,
            TemperatureStrategy tempStrategy,
            HumidityStrategy humStrategy,
            CO2Strategy co2Strategy,
            LightStrategy lightStrategy) {

        this.analyzer = analyzer;
        this.tempStrategy = tempStrategy;
        this.humStrategy = humStrategy;
        this.co2Strategy = co2Strategy;
        this.lightStrategy = lightStrategy;

        // Subscribe to the analyzer
        this.analyzer.attach(this);
    }

    @Override
    public void update() {
        // 1. Get the current measurement
        Measurement m = analyzer.getCurrentMeasurement();
        if (m == null) return;

        // 2. Save metadata for the API response
        this.basedOnMeasurementId = m.getId();
        this.timestamp = LocalDateTime.now();

        // 3. PHYSICAL MUTE LOGIC
        if (m.isButtonPressed()) {
            this.alarmEnabled = false;
            this.buzzerStatus = false;
        }

        // 4. RESET ACTUATORS (Assume everything is OK before checking)
        resetActuators();

        // 5. EVALUATE STRATEGIES AND ACTIVATE HARDWARE

        // Temperature logic
        if (tempStrategy.isTooHot()) {
            setRGBCommand("#FF0000"); // RED for Heat
            setFanCommand(true);      // Cool down
            if (alarmEnabled) setBuzzerCommand(true);
        } else if (tempStrategy.isTooCold()) {
            setRGBCommand("#0000FF"); // BLUE for Cold
            setResistorCommand(true); // Heat up
            if (alarmEnabled) setBuzzerCommand(true);
        }

        // Humidity logic
        if (humStrategy.isWateringRequired()) {
            setRGBCommand("#FFFFFF"); // WHITE for watering
            setMotorCommand(true);    // Turn on water pump
            if (alarmEnabled) setBuzzerCommand(true);
        }

        // CO2 logic
        if (co2Strategy.isVentilationRequired()) {
            setRGBCommand("#FFC0CB"); // PINK for bad air quality
            setFanCommand(true);      // Ventilate
            if (alarmEnabled) setBuzzerCommand(true);
        }

        // Light logic (Corregido según nuestra charla para el RF-12 y ALG-05) [4, 5]
        if (lightStrategy.getLightDeficit() > 0) {
            setRGBCommand("#FFFF00"); // YELLOW for low light
            setLEDStripCommand(80);   // Turn on LED strip
            if (alarmEnabled) setBuzzerCommand(true);
        }
    }

    private void resetActuators() {
        this.fanStatus = false;
        this.buzzerStatus = false;
        this.motorStatus = false;
        this.resistorStatus = false;
        this.rgbColorCommand = "#00FF00"; // GREEN [3]
        this.ledIntensityCommand = 0;
    }

    // --- SETTERS ---
    public void setRGBCommand(String color) { this.rgbColorCommand = color; }
    public void setBuzzerCommand(boolean active) { this.buzzerStatus = active; }
    public void setMotorCommand(boolean active) { this.motorStatus = active; }
    public void setResistorCommand(boolean active) { this.resistorStatus = active; }
    public void setFanCommand(boolean active) { this.fanStatus = active; }
    public void setLEDStripCommand(int intensity) { this.ledIntensityCommand = intensity; }

    // --- GETTERS ---
    public boolean isFanStatus() { return fanStatus; }
    public boolean isBuzzerStatus() { return buzzerStatus; }
    public boolean isMotorStatus() { return motorStatus; }
    public boolean isResistorStatus() { return resistorStatus; }
    public String getRgbColorCommand() { return rgbColorCommand; }
    public int getLedIntensityCommand() { return ledIntensityCommand; }
    public Integer getBasedOnMeasurementId() { return basedOnMeasurementId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isAlarmMuted() {return !this.alarmEnabled;}
}