package com.iot.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.Test;

class CO2StrategyTest {

    private static final float DELTA = 0.0001f;

    @Test
    void shouldRequireVentilationAboveMaximumCO2() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        strategy.evaluate(1500.0f);

        assertTrue(strategy.isVentilationRequired());
    }

    @Test
    void shouldNotRequireVentilationBelowMaximumCO2() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        strategy.evaluate(800.0f);

        assertFalse(strategy.isVentilationRequired());
    }

    @Test
    void shouldConsiderExactMaximumCO2Normal() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        strategy.evaluate(1000.0f);

        assertFalse(strategy.isVentilationRequired());
    }

    @Test
    void shouldProcessMeasurementCO2() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        Measurement measurement = new Measurement();
        measurement.setCo2(1200.0f);

        strategy.process(measurement);

        assertTrue(strategy.isVentilationRequired());
    }

    @Test
    void shouldUpdateMaximumCO2() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        strategy.setMaxCO2(1500.0f);

        assertEquals(
            1500.0f,
            strategy.getMaxCO2(),
            DELTA
        );
    }

    @Test
    void shouldRejectCO2OutsideAllowedRange() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(5001.0f)
        );
    }

    @Test
    void shouldRejectInvalidMaximumCO2() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new CO2Strategy(-1.0f)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new CO2Strategy(5001.0f)
        );
    }

    @Test
    void shouldRejectNonFiniteCO2() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.evaluate(Float.NaN)
        );
    }

    @Test
    void shouldRejectNullMeasurement() {
        CO2Strategy strategy =
            new CO2Strategy(1000.0f);

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.process(null)
        );
    }
}
