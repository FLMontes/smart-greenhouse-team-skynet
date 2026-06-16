package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import java.time.LocalDateTime;

@Component
public class HumidityStrategy implements IAlgorithmStrategy {

    private static final float DEFAULT_MIN_HUMIDITY = 30.0f;
    private float minHumidity;

    public HumidityStrategy() {
        this.minHumidity = DEFAULT_MIN_HUMIDITY;
    }

    @Override
    public AlgorithmResult process(AnalysisContext context) {
        if (context == null || context.getAveragedMeasurement() == null) {
            throw new IllegalArgumentException("AnalysisContext must not be null");
        }

        // 1. Tomamos la humedad promediada
        float avgHumidity = context.getAveragedMeasurement().getHumidity();

        // 2. Evaluamos si necesita riego (1.0 = Sí, 0.0 = No)
        float statusValue = (avgHumidity < minHumidity) ? 1.0f : 0.0f;

        // 3. Devolvemos el resultado
        return new AlgorithmResult(
                "HumidityStrategy",
                "Evaluates humidity to determine watering needs.",
                "PostgreSQL measurements table",
                "Last " + context.getSourceMeasurements().size() + " stored measurements",
                "wateringRequired",
                statusValue,
                "boolean",
                LocalDateTime.now()
        );
    }

    public float getMinHumidity() { return minHumidity; }
    public void setMinHumidity(float minHumidity) { this.minHumidity = minHumidity; }
}
