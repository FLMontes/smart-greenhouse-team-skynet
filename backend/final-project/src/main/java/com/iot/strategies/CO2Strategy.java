package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import java.time.LocalDateTime;

@Component
public class CO2Strategy implements IAlgorithmStrategy {

    private static final float DEFAULT_MAX_CO2 = 1000.0f;
    private float maxCO2;

    public CO2Strategy() {
        this.maxCO2 = DEFAULT_MAX_CO2;
    }

    @Override
    public AlgorithmResult process(AnalysisContext context) {
        if (context == null || context.getAveragedMeasurement() == null) {
            throw new IllegalArgumentException("AnalysisContext must not be null");
        }

        // 1. Tomamos el CO2 promediado
        float avgCo2 = context.getAveragedMeasurement().getCo2();

        // 2. Evaluamos si necesita ventilación (1.0 = Sí, 0.0 = No)
        float statusValue = (avgCo2 > maxCO2) ? 1.0f : 0.0f;

        return new AlgorithmResult(
                "CO2Strategy",
                "Evaluates CO2 concentration to determine ventilation needs.",
                "PostgreSQL measurements table",
                "Last " + context.getSourceMeasurements().size() + " stored measurements",
                "ventilationRequired",
                statusValue,
                "boolean",
                LocalDateTime.now()
        );
    }

    public float getMaxCO2() { return maxCO2; }
    public void setMaxCO2(float maxCO2) { this.maxCO2 = maxCO2; }
}
