package com.iot.strategies;

import org.junit.jupiter.api.Test;

class TemperatureStrategyTest {

    @Test
    void shouldDetectLowTemperature() {

        TemperatureStrategy strategy =
                new TemperatureStrategy();

        assertTrue(strategy.isOutOfBounds(10));
    }

    @Test
    void shouldAcceptNormalTemperature() {

        TemperatureStrategy strategy =
                new TemperatureStrategy();

        assertFalse(strategy.isOutOfBounds(20));
    }
}