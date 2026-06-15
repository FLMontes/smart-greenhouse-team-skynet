package com.iot.models.dto;

import java.time.LocalDateTime;

public class Alert {

    private Integer id; // Cambiado a Integer
    private String type;
    private String message;
    private String severity;
    private boolean active;
    private Integer relatedMeasurementId; // Cambiado a Integer
    private LocalDateTime timestamp;

    public Alert() {
    }

    // --- GETTERS ---
    public Integer getId() { return id; }
    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
    public boolean isActive() { return active; }
    public Integer getRelatedMeasurementId() { return relatedMeasurementId; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // --- SETTERS ---
    public void setId(Integer id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setMessage(String message) { this.message = message; }
    public void setSeverity(String severity) { this.severity = severity; }
    public void setActive(boolean active) { this.active = active; }
    public void setRelatedMeasurementId(Integer relatedMeasurementId) { this.relatedMeasurementId = relatedMeasurementId; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}