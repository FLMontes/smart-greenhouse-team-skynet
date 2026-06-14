package com.iot.strategies;

import com.iot.models.entities.Measurement;

public class LightStrategy implements IAlgorithmStrategy {

    private static final float MIN_ALLOWED_LIGHT = 0.0f;
    private static final float MAX_ALLOWED_LIGHT = 100000.0f;

    private float targetLightLevel;
    private float lightDeficit;

    public LightStrategy(float targetLightLevel) {
        setTargetLightLevel(targetLightLevel);
    }

    @Override
    public void process(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(
                "Measurement must not be null"
            );
        }

        evaluate(measurement.getLight());
    }

    public void evaluate(float lightLevel) {
        validateLightLevel(lightLevel);

        lightDeficit = Math.max(
            0.0f,
            targetLightLevel - lightLevel
        );
    }

    public float getLightDeficit() {
        return lightDeficit;
    }

    public float getTargetLightLevel() {
        return targetLightLevel;
    }

    public void setTargetLightLevel(float targetLightLevel) {
        validateLightLevel(targetLightLevel);
        this.targetLightLevel = targetLightLevel;
    }

    private void validateLightLevel(float lightLevel) {
        if (!Float.isFinite(lightLevel)
            || lightLevel < MIN_ALLOWED_LIGHT
            || lightLevel > MAX_ALLOWED_LIGHT) {

            throw new IllegalArgumentException(
                "Light level must be between 0 and 100000"
            );
        }
    }
}
