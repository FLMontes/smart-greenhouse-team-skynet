package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import java.time.LocalDateTime;

@Component
public class TemperatureStrategy implements IAlgorithmStrategy {

    private static final float DEFAULT_MIN_TEMPERATURE = 15.0f;
    private static final float DEFAULT_MAX_TEMPERATURE = 35.0f;

    private float minTemperature;
    private float maxTemperature;

    public TemperatureStrategy() {
        this.minTemperature = DEFAULT_MIN_TEMPERATURE;
        this.maxTemperature = DEFAULT_MAX_TEMPERATURE;
    }

    @Override
    public AlgorithmResult process(AnalysisContext context) {
        if (context == null || context.getAveragedMeasurement() == null) {
            throw new IllegalArgumentException("AnalysisContext and averagedMeasurement must not be null");
        }

        // 1. Obtenemos la temperatura promediada del contexto (no el dato crudo)
        float avgTemp = context.getAveragedMeasurement().getTemperature();

        // 2. Evaluamos la lógica (1.0 = Too Hot, -1.0 = Too Cold, 0.0 = Normal)
        float statusValue = 0.0f;
        String outputName = "normal";

        if (avgTemp > maxTemperature) {
            statusValue = 1.0f;
            outputName = "isTooHot";
        } else if (avgTemp < minTemperature) {
            statusValue = -1.0f;
            outputName = "isTooCold";
        }

        // 3. Devolvemos el resultado inmutable, tal como pide el contrato OpenAPI
        return new AlgorithmResult(
                "TemperatureStrategy",
                "Evaluates temperature boundaries (min/max) and generates cold/heat alerts.",
                "PostgreSQL measurements table",
                "Last " + context.getSourceMeasurements().size() + " stored measurements",
                outputName,
                statusValue,
                "status_code",
                LocalDateTime.now()
        );
    }

    // Mantenemos los getters y setters para los umbrales si quisieras cambiarlos en el futuro
    public float getMinTemperature() { return minTemperature; }
    public void setMinTemperature(float minTemperature) { this.minTemperature = minTemperature; }
    public float getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(float maxTemperature) { this.maxTemperature = maxTemperature; }
}