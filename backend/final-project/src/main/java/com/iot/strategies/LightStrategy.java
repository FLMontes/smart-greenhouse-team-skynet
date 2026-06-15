package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import java.time.LocalDateTime;

@Component
public class LightStrategy implements IAlgorithmStrategy {

    private static final float DEFAULT_TARGET_LIGHT = 500.0f;
    private float targetLightLevel;

    public LightStrategy() {
        this.targetLightLevel = DEFAULT_TARGET_LIGHT;
    }

    @Override
    public AlgorithmResult process(AnalysisContext context) {
        if (context == null || context.getAveragedMeasurement() == null) {
            throw new IllegalArgumentException("AnalysisContext must not be null");
        }

        // 1. Tomamos la luz promediada
        float avgLight = context.getAveragedMeasurement().getLight();

        // 2. Calculamos el déficit de luz real
        float lightDeficit = Math.max(0.0f, targetLightLevel - avgLight);

        return new AlgorithmResult(
                "LightStrategy",
                "Calculates light deficit to control LED strip intensity.",
                "PostgreSQL measurements table",
                "Last " + context.getSourceMeasurements().size() + " stored measurements",
                "lightDeficit",
                lightDeficit,
                "lux",
                LocalDateTime.now()
        );
    }

    public float getTargetLightLevel() { return targetLightLevel; }
    public void setTargetLightLevel(float targetLightLevel) { this.targetLightLevel = targetLightLevel; }
}