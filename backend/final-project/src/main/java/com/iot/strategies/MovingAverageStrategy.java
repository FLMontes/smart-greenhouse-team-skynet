package com.iot.strategies;

public class MovingAverageStrategy {

    public double calculate(double[] values) {

        double sum = 0;

        for(double value : values) {
            sum += value;
        }

        return sum / values.length;
    }
}