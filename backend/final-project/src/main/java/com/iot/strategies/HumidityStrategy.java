package com.iot.strategies;

import com.iot.models.entities.Measurement;
import org.springframework.stereotype.Component;

@Component
public class HumidityStrategy implements IAlgorithmStrategy {

    private static final float DEFAULT_MIN_HUMIDITY = 40.0f;
    private static final float MIN_ALLOWED_HUMIDITY = 0.0f;
    private static final float MAX_ALLOWED_HUMIDITY = 100.0f;

    private float minHumidity;
    private boolean wateringRequired;

    public HumidityStrategy() {
        this(DEFAULT_MIN_HUMIDITY);
    }

    public HumidityStrategy(float minHumidity) {
        setMinHumidity(minHumidity);
    }

    @Override
    public void process(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(
                "Measurement must not be null"
            );
        }

        evaluate(measurement.getHumidity());
    }

    public void evaluate(float humidity) {
        validateHumidity(humidity);
        wateringRequired = humidity < minHumidity;
    }

    public boolean isWateringRequired() {
        return wateringRequired;
    }

    public float getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(float minHumidity) {
        validateHumidity(minHumidity);
        this.minHumidity = minHumidity;
    }

    private void validateHumidity(float humidity) {
        if (!Float.isFinite(humidity)
            || humidity < MIN_ALLOWED_HUMIDITY
            || humidity > MAX_ALLOWED_HUMIDITY) {

            throw new IllegalArgumentException(
                "Humidity must be between 0 and 100"
            );
        }
    }
}
