package com.iot.strategy;

public class TemperatureStrategy {

    public boolean isOutOfBounds(double value) {
        return value > 35 || value < 5;
    }
}