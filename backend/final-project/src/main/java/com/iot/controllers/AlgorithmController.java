package com.iot.controllers;

import com.iot.models.dto.Alert;
import com.iot.models.dto.AlgorithmDefinition;
import com.iot.models.dto.AlgorithmResult;
import com.iot.services.EnvironmentalAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/algorithms")
public class AlgorithmController {

    private final EnvironmentalAnalyzer analyzer;

    public AlgorithmController(EnvironmentalAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @GetMapping
    public ResponseEntity<List<AlgorithmDefinition>> getAlgorithms() {
        // Datos estáticos que describen nuestros algoritmos según el contrato
        List<AlgorithmDefinition> definitions = List.of(
                new AlgorithmDefinition("MovingAverageStrategy", "Calculates the moving average using recent stored measurements.", "PostgreSQL measurements table", "Recent measurement values from stored records.", "averageValue", "various"),
                new AlgorithmDefinition("TemperatureStrategy", "Evaluates temperature boundaries and generates cold/heat alerts.", "PostgreSQL measurements table", "Latest averaged temperature value.", "temperatureStatus", "status"),
                new AlgorithmDefinition("CO2Strategy", "Evaluates CO2 concentration to determine ventilation needs.", "PostgreSQL measurements table", "Latest averaged CO2 value.", "ventilationRequired", "boolean"),
                new AlgorithmDefinition("HumidityStrategy", "Evaluates humidity to determine watering needs.", "PostgreSQL measurements table", "Latest averaged humidity value.", "wateringRequired", "boolean"),
                new AlgorithmDefinition("LightStrategy", "Calculates light deficit to control LED strip intensity.", "PostgreSQL measurements table", "Latest averaged light value.", "lightDeficit", "lux")
        );
        return ResponseEntity.ok(definitions);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<AlgorithmResult>> getLatestAlgorithmResults() {
        // Le pedimos al analizador los últimos resultados que calculó
        List<AlgorithmResult> results = analyzer.getLatestAlgorithmResults();

        if (results == null || results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    @RestController
    @RequestMapping("/api/alerts")
    public static class AlertController {

        private final EnvironmentalAnalyzer analyzer;

        public AlertController(EnvironmentalAnalyzer analyzer) {
            this.analyzer = analyzer;
        }

        @GetMapping
        public ResponseEntity<List<Alert>> getAlerts() {
            // Obtenemos la lista de alertas activas desde el analizador
            List<Alert> activeAlerts = analyzer.getActiveAlerts();

            // El contrato espera un array. Si está vacío, devolvemos un array vacío [] con status 200 OK.
            return ResponseEntity.ok(activeAlerts);
        }
    }
}