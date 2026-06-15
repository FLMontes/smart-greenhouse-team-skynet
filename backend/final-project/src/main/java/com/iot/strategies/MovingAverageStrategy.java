package com.iot.strategies;

import com.iot.models.entities.Measurement;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class MovingAverageStrategy implements IAlgorithmStrategy {

    private static final int DEFAULT_WINDOW_SIZE = 5;

    private final int windowSize;
    private final Deque<Double> values;
    private double lastAverage;

    public MovingAverageStrategy() {
        this(DEFAULT_WINDOW_SIZE);
    }

    public MovingAverageStrategy(int windowSize) {
        if (windowSize <= 0) {
            throw new IllegalArgumentException(
                "Window size must be greater than zero"
            );
        }

        this.windowSize = windowSize;
        this.values = new ArrayDeque<>();
    }

    @Override
    public void process(Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException(
                "Measurement must not be null"
            );
        }

        addValue(measurement.getTemperature());
        lastAverage = calculate(currentValues());
    }

    public double calculate(double[] inputValues) {
        validateValues(inputValues);

        double sum = 0.0;

        for (double value : inputValues) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(
                    "Values must contain only finite numbers"
                );
            }

            sum += value;
        }

        lastAverage = sum / inputValues.length;
        return lastAverage;
    }

    public double getLastAverage() {
        return lastAverage;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getCurrentWindowSize() {
        return values.size();
    }

    private void addValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                "Temperature must be a finite number"
            );
        }

        if (values.size() == windowSize) {
            values.removeFirst();
        }

        values.addLast(value);
    }

    private double[] currentValues() {
        return values.stream()
            .mapToDouble(Double::doubleValue)
            .toArray();
    }

    private void validateValues(double[] inputValues) {
        if (inputValues == null || inputValues.length == 0) {
            throw new IllegalArgumentException(
                "Values must not be null or empty"
            );
        }
    }
}
