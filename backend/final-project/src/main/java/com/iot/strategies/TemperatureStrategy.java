package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.entities.Measurement;

@Component
public class TemperatureStrategy implements IAlgorithmStrategy {

    private static final float DEFAULT_MIN_TEMPERATURE = 15.0f;
    private static final float DEFAULT_MAX_TEMPERATURE = 35.0f;
    private static final float MIN_ALLOWED_TEMPERATURE = -10.0f;
    private static final float MAX_ALLOWED_TEMPERATURE = 60.0f;

    private float minTemperature;
    private float maxTemperature;
    private boolean tooCold;
    private boolean tooHot;

    public TemperatureStrategy() {
        this(
            DEFAULT_MIN_TEMPERATURE,
            DEFAULT_MAX_TEMPERATURE
        );
    }

    public TemperatureStrategy(
        float minTemperature,
        float maxTemperature
    ) {
        validateTemperature(minTemperature);
        validateTemperature(maxTemperature);
        validateRange(minTemperature, maxTemperature);

        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    @Override
    public void process(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(
                "Measurement must not be null"
            );
        }

        evaluate(measurement.getTemperature());
    }

    public void evaluate(float temperature) {
        validateTemperature(temperature);

        tooCold = temperature < minTemperature;
        tooHot = temperature > maxTemperature;
    }

    public boolean isOutOfBounds(double temperature) {
        if (!Double.isFinite(temperature)
            || temperature < MIN_ALLOWED_TEMPERATURE
            || temperature > MAX_ALLOWED_TEMPERATURE) {

            throw new IllegalArgumentException(
                "Temperature must be between -10 and 60"
            );
        }

        return temperature < minTemperature
            || temperature > maxTemperature;
    }

    public boolean isTooCold() {
        return tooCold;
    }

    public boolean isTooHot() {
        return tooHot;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        validateTemperature(minTemperature);
        validateRange(minTemperature, maxTemperature);
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        validateTemperature(maxTemperature);
        validateRange(minTemperature, maxTemperature);
        this.maxTemperature = maxTemperature;
    }

    private void validateTemperature(float temperature) {
        if (!Float.isFinite(temperature)
            || temperature < MIN_ALLOWED_TEMPERATURE
            || temperature > MAX_ALLOWED_TEMPERATURE) {

            throw new IllegalArgumentException(
                "Temperature must be between -10 and 60"
            );
        }
    }

    private void validateRange(
        float minTemperature,
        float maxTemperature
    ) {
        if (minTemperature > maxTemperature) {
            throw new IllegalArgumentException(
                "Minimum temperature must not exceed maximum temperature"
            );
        }
    }
}
