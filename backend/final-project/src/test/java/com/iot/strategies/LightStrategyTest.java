package com.iot.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.Test;

class LightStrategyTest {

    private static final float DELTA = 0.0001f;

    @Test
    void shouldCalculateDeficitBelowTarget() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        strategy.evaluate(700.0f);

        assertEquals(
            300.0f,
            strategy.getLightDeficit(),
            DELTA
        );
    }

    @Test
    void shouldReturnZeroWhenLightEqualsTarget() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        strategy.evaluate(1000.0f);

        assertEquals(
            0.0f,
            strategy.getLightDeficit(),
            DELTA
        );
    }

    @Test
    void shouldReturnZeroWhenLightExceedsTarget() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        strategy.evaluate(1500.0f);

        assertEquals(
            0.0f,
            strategy.getLightDeficit(),
            DELTA
        );
    }

    @Test
    void shouldProcessMeasurementLightLevel() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        Measurement measurement = new Measurement();
        measurement.setLight(650.0f);

        strategy.process(measurement);

        assertEquals(
            350.0f,
            strategy.getLightDeficit(),
            DELTA
        );
    }

    @Test
    void shouldUpdateTargetLightLevel() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        strategy.setTargetLightLevel(1500.0f);

        assertEquals(
            1500.0f,
            strategy.getTargetLightLevel(),
            DELTA
        );
    }

    @Test
    void shouldRejectLightOutsideAllowedRange() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(100001.0f)
        );
    }

    @Test
    void shouldRejectInvalidTargetLightLevel() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new LightStrategy(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new LightStrategy(100001.0f)
        );
    }

    @Test
    void shouldRejectNonFiniteLightLevel() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(Float.NaN)
        );
    }

    @Test
    void shouldRejectNullMeasurement() {
        LightStrategy strategy =
            new LightStrategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.process(null)
        );
    }
}
