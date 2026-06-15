package com.iot.models.dto;

import com.iot.models.entities.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisContext {

    // Esta es la medición "virtual" que contiene los promedios.
    // Las estrategias leerán la temperatura, luz, etc. de aquí.
    private Measurement averagedMeasurement;

    // Guardamos la lista de mediciones reales que usamos para calcular el promedio (trazabilidad)
    private List<Measurement> sourceMeasurements;

    // El ID de la última medición real que disparó este análisis
    private Integer relatedMeasurementId;

    // El momento exacto en que el backend calculó este contexto
    private LocalDateTime timestamp;
}