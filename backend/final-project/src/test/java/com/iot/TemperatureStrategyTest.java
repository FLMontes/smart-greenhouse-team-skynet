import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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