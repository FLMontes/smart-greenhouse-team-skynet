package com.iot.observers;

import com.iot.models.entities.Measurement;
import com.iot.services.EnvironmentalAnalyzer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class WebDashboardObserver implements IObserver {

    private String frontendEndpoint = "/api/measurements/latest";
    private String lastPayload = "{}";

    private final EnvironmentalAnalyzer analyzer;
    private final ObjectMapper objectMapper; // <-- Agregamos el mapeador de JSON

    // Constructor Injection
    public WebDashboardObserver(EnvironmentalAnalyzer analyzer, ObjectMapper objectMapper) {
        this.analyzer = analyzer;
        this.objectMapper = objectMapper;
        this.analyzer.attach(this);
    }

    @Override
    public void update() {
        Measurement m = analyzer.getCurrentMeasurement();

        if (m == null) return;

        try {
            // ¡Magia! Convierte toda la medición a un JSON perfecto automáticamente
            this.lastPayload = objectMapper.writeValueAsString(m);
            sendToFrontend();
        } catch (Exception e) {
            System.err.println("Error al convertir la medición a JSON: " + e.getMessage());
        }
    }

    public void sendToFrontend() {
        System.out.println("WebDashboardObserver: Preparing data for " + frontendEndpoint);
        System.out.println("Payload ready for dashboard: " + lastPayload);
    }

    // --- GETTERS & SETTERS ---
    public String getFrontendEndpoint() { return frontendEndpoint; }
    public void setFrontendEndpoint(String frontendEndpoint) { this.frontendEndpoint = frontendEndpoint; }
    public String getLastPayload() { return lastPayload; }
}