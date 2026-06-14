package com.iot.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.Test;

class MovingAverageStrategyTest {

    private static final double DELTA = 0.0001;

    @Test
    void shouldCalculateAverageCorrectly() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy();

        double[] values = {20.0, 22.0, 24.0};

        double result = strategy.calculate(values);

        assertEquals(22.0, result, DELTA);
        assertEquals(22.0, strategy.getLastAverage(), DELTA);
    }

    @Test
    void shouldProcessMeasurementsUsingMovingWindow() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy(3);

        strategy.process(createMeasurement(20.0f));
        strategy.process(createMeasurement(22.0f));
        strategy.process(createMeasurement(24.0f));

        assertEquals(22.0, strategy.getLastAverage(), DELTA);
        assertEquals(3, strategy.getCurrentWindowSize());
    }

    @Test
    void shouldDiscardOldestValueWhenWindowIsFull() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy(3);

        strategy.process(createMeasurement(20.0f));
        strategy.process(createMeasurement(22.0f));
        strategy.process(createMeasurement(24.0f));
        strategy.process(createMeasurement(30.0f));

        assertEquals(
            25.3333,
            strategy.getLastAverage(),
            DELTA
        );
        assertEquals(3, strategy.getCurrentWindowSize());
    }

    @Test
    void shouldRejectInvalidWindowSize() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new MovingAverageStrategy(0)
        );
    }

    @Test
    void shouldRejectNullOrEmptyValues() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.calculate(null)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.calculate(new double[]{})
        );
    }

    @Test
    void shouldRejectNonFiniteValues() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.calculate(
                new double[]{20.0, Double.NaN}
            )
        );
    }

    @Test
    void shouldRejectNullMeasurement() {
        MovingAverageStrategy strategy =
            new MovingAverageStrategy();

        assertThrows(
            IllegalArgumentException.class,
            () -> strategy.process(null)
        );
    }

    private Measurement createMeasurement(float temperature) {
        Measurement measurement = new Measurement();
        measurement.setTemperature(temperature);
        return measurement;
    }
}
