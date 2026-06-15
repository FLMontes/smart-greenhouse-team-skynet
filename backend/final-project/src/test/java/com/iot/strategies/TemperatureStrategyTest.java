package com.iot.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.Test;

class TemperatureStrategyTest {

    private static final float DELTA = 0.0001f;

    @Test
    void shouldDetectLowTemperature() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        strategy.evaluate(10.0f);

        assertTrue(strategy.isTooCold());
        assertFalse(strategy.isTooHot());
        assertTrue(strategy.isOutOfBounds(10.0));
    }

    @Test
    void shouldDetectHighTemperature() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        strategy.evaluate(40.0f);

        assertFalse(strategy.isTooCold());
        assertTrue(strategy.isTooHot());
        assertTrue(strategy.isOutOfBounds(40.0));
    }

    @Test
    void shouldAcceptNormalTemperature() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        strategy.evaluate(20.0f);

        assertFalse(strategy.isTooCold());
        assertFalse(strategy.isTooHot());
        assertFalse(strategy.isOutOfBounds(20.0));
    }

    @Test
    void shouldConsiderBoundaryValuesNormal() {
        TemperatureStrategy strategy =
            new TemperatureStrategy(15.0f, 35.0f);

        strategy.evaluate(15.0f);
        assertFalse(strategy.isTooCold());
        assertFalse(strategy.isTooHot());

        strategy.evaluate(35.0f);
        assertFalse(strategy.isTooCold());
        assertFalse(strategy.isTooHot());
    }

    @Test
    void shouldProcessMeasurementTemperature() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        Measurement measurement = new Measurement();
        measurement.setTemperature(10.0f);

        strategy.process(measurement);

        assertTrue(strategy.isTooCold());
        assertFalse(strategy.isTooHot());
    }

    @Test
    void shouldUseDefaultTemperatureLimits() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        assertEquals(
            15.0f,
            strategy.getMinTemperature(),
            DELTA
        );
        assertEquals(
            35.0f,
            strategy.getMaxTemperature(),
            DELTA
        );
    }

    @Test
    void shouldUpdateTemperatureLimits() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        strategy.setMinTemperature(12.0f);
        strategy.setMaxTemperature(30.0f);

        assertEquals(
            12.0f,
            strategy.getMinTemperature(),
            DELTA
        );
        assertEquals(
            30.0f,
            strategy.getMaxTemperature(),
            DELTA
        );
    }

    @Test
    void shouldRejectInvalidTemperatureRange() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TemperatureStrategy(40.0f, 20.0f)
        );
    }

    @Test
    void shouldRejectTemperatureOutsideAllowedRange() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(-11.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(61.0f)
        );
    }

    @Test
    void shouldRejectNullMeasurement() {
        TemperatureStrategy strategy =
            new TemperatureStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.process(null)
        );
    }
}
