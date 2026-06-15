package com.iot.observers;

import com.iot.models.dto.AlgorithmResult;
import com.iot.services.EnvironmentalAnalyzer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class HardwareAlarmObserver implements IObserver {

    private boolean fanStatus;
    private boolean buzzerStatus;
    private boolean motorStatus;
    private boolean resistorStatus;
    private String rgbColorCommand = "#00FF00"; // GREEN
    private int ledIntensityCommand;
    private Integer basedOnMeasurementId;
    private LocalDateTime timestamp;
    private boolean alarmMuted = false;

    private final EnvironmentalAnalyzer analyzer;

    // Fíjate que ya no inyectamos las estrategias aquí. ¡Código mucho más limpio!
    public HardwareAlarmObserver(EnvironmentalAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.analyzer.attach(this);
    }

    @Override
    public void update() {
        if (analyzer.getCurrentMeasurement() == null) return;

        this.basedOnMeasurementId = analyzer.getCurrentMeasurement().getId();
        this.timestamp = LocalDateTime.now();

        // Lógica de Silencio (Mute)
        // Lógica de Silencio (Mute)
        if (analyzer.getCurrentMeasurement().isButtonPressed() != null &&
                analyzer.getCurrentMeasurement().isButtonPressed()) {
            this.alarmMuted = true;
            this.buzzerStatus = false;
        } else {
            this.alarmMuted = false;
        }

        resetActuators();

        // 1. Le pedimos al Analizador los resultados procesados
        List<AlgorithmResult> results = analyzer.getLatestAlgorithmResults();

        // 2. Encendemos actuadores basados en esos resultados
        for (AlgorithmResult res : results) {
            switch (res.getAlgorithm()) {
                case "TemperatureStrategy":
                    if (res.getValue() == 1.0f) { // Too Hot
                        this.rgbColorCommand = "#FF0000"; // RED
                        this.fanStatus = true;
                        if (!alarmMuted) this.buzzerStatus = true;
                    } else if (res.getValue() == -1.0f) { // Too Cold
                        this.rgbColorCommand = "#0000FF"; // BLUE
                        this.resistorStatus = true;
                        if (!alarmMuted) this.buzzerStatus = true;
                    }
                    break;
                case "HumidityStrategy":
                    if (res.getValue() == 1.0f) { // Watering required
                        if (this.rgbColorCommand.equals("#00FF00")) this.rgbColorCommand = "#FFFFFF"; // WHITE
                        this.motorStatus = true;
                    }
                    break;
                case "CO2Strategy":
                    if (res.getValue() == 1.0f) { // Ventilation required
                        if (this.rgbColorCommand.equals("#00FF00")) this.rgbColorCommand = "#FFC0CB"; // PINK
                        this.fanStatus = true;
                        if (!alarmMuted) this.buzzerStatus = true;
                    }
                    break;
                case "LightStrategy":
                    if (res.getValue() > 0) { // Light deficit
                        if (this.rgbColorCommand.equals("#00FF00")) this.rgbColorCommand = "#FFFF00"; // YELLOW
                        this.ledIntensityCommand = 80;
                    }
                    break;
            }
        }
    }

    private void resetActuators() {
        this.fanStatus = false;
        if (!this.alarmMuted) this.buzzerStatus = false;
        this.motorStatus = false;
        this.resistorStatus = false;
        this.rgbColorCommand = "#00FF00";
        this.ledIntensityCommand = 0;
    }

    // --- GETTERS (Mantenemos los getters porque el ActuatorController los necesita) ---
    public boolean isFanStatus() { return fanStatus; }
    public boolean isBuzzerStatus() { return buzzerStatus; }
    public boolean isMotorStatus() { return motorStatus; }
    public boolean isResistorStatus() { return resistorStatus; }
    public String getRgbColorCommand() { return rgbColorCommand; }
    public int getLedIntensityCommand() { return ledIntensityCommand; }
    public Integer getBasedOnMeasurementId() { return basedOnMeasurementId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isAlarmMuted() { return alarmMuted; }
}