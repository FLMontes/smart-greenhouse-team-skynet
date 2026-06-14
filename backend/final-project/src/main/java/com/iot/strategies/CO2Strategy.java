package com.iot.strategies;

import com.iot.models.entities.Measurement;

public class CO2Strategy implements IAlgorithmStrategy {

    private static final float MIN_ALLOWED_CO2 = 0.0f;
    private static final float MAX_ALLOWED_CO2 = 5000.0f;

    private float maxCO2;
    private boolean ventilationRequired;

    public CO2Strategy(float maxCO2) {
        setMaxCO2(maxCO2);
    }

    @Override
    public void process(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(
                "Measurement must not be null"
            );
        }

        evaluate(measurement.getCo2());
    }

    public void evaluate(float co2) {
        validateCO2(co2);
        ventilationRequired = co2 > maxCO2;
    }

    public boolean isVentilationRequired() {
        return ventilationRequired;
    }

    public float getMaxCO2() {
        return maxCO2;
    }

    public void setMaxCO2(float maxCO2) {
        validateCO2(maxCO2);
        this.maxCO2 = maxCO2;
    }

    private void validateCO2(float co2) {
        if (!Float.isFinite(co2)
            || co2 < MIN_ALLOWED_CO2
            || co2 > MAX_ALLOWED_CO2) {

            throw new IllegalArgumentException(
                "CO2 must be between 0 and 5000"
            );
        }
    }
}
