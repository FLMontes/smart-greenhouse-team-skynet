package com.iot.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.Test;

class HumidityStrategyTest {

    private static final float DELTA = 0.0001f;

    @Test
    void shouldRequireWateringBelowMinimumHumidity() {
        HumidityStrategy strategy =
            new HumidityStrategy(40.0f);

        strategy.evaluate(30.0f);

        assertTrue(strategy.isWateringRequired());
    }

    @Test
    void shouldNotRequireWateringAboveMinimumHumidity() {
        HumidityStrategy strategy =
            new HumidityStrategy(40.0f);

        strategy.evaluate(60.0f);

        assertFalse(strategy.isWateringRequired());
    }

    @Test
    void shouldConsiderExactMinimumHumidityNormal() {
        HumidityStrategy strategy =
            new HumidityStrategy(40.0f);

        strategy.evaluate(40.0f);

        assertFalse(strategy.isWateringRequired());
    }

    @Test
    void shouldProcessMeasurementHumidity() {
        HumidityStrategy strategy =
            new HumidityStrategy(40.0f);

        Measurement measurement = new Measurement();
        measurement.setHumidity(25.0f);

        strategy.process(measurement);

        assertTrue(strategy.isWateringRequired());
    }

    @Test
    void shouldUseDefaultMinimumHumidity() {
        HumidityStrategy strategy =
            new HumidityStrategy();

        assertEquals(
            40.0f,
            strategy.getMinHumidity(),
            DELTA
        );
    }

    @Test
    void shouldUpdateMinimumHumidity() {
        HumidityStrategy strategy =
            new HumidityStrategy();

        strategy.setMinHumidity(50.0f);

        assertEquals(
            50.0f,
            strategy.getMinHumidity(),
            DELTA
        );
    }

    @Test
    void shouldRejectHumidityOutsideAllowedRange() {
        HumidityStrategy strategy =
            new HumidityStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(101.0f)
        );
    }

    @Test
    void shouldRejectInvalidMinimumHumidity() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new HumidityStrategy(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new HumidityStrategy(101.0f)
        );
    }

    @Test
    void shouldRejectNullMeasurement() {
        HumidityStrategy strategy =
            new HumidityStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.process(null)
        );
    }
}
