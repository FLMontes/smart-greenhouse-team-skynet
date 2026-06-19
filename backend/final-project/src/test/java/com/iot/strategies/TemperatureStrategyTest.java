package com.iot.strategies;

import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemperatureStrategyTest {
    private static final float DELTA = 0.0001f;
    private TemperatureStrategy strategy;
    @BeforeEach void setUp() { strategy = new TemperatureStrategy(); strategy.setMinTemperature(15.0f); strategy.setMaxTemperature(35.0f); }
    private AnalysisContext createContext(float temperatureValue) { Measurement m = new Measurement(); m.setTemperature(temperatureValue); return new AnalysisContext(m, List.of(m), 1, LocalDateTime.now()); }
    @Test void process_shouldReturnTooCold_whenTempIsBelowMin() { AnalysisContext context = createContext(10.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(-1.0f, result.getValue(), DELTA); assertEquals("isTooCold", result.getOutputName()); }
    @Test void process_shouldReturnTooHot_whenTempIsAboveMax() { AnalysisContext context = createContext(40.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(1.0f, result.getValue(), DELTA); assertEquals("isTooHot", result.getOutputName()); }
    @Test void process_shouldReturnNormal_whenTempIsWithinBounds() { AnalysisContext context = createContext(25.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0f, result.getValue(), DELTA); assertEquals("normal", result.getOutputName()); }
    @Test void process_shouldReturnNormal_whenTempIsExactlyAtBounds() { AnalysisContext minContext = createContext(15.0f); AlgorithmResult minResult = strategy.process(minContext); assertEquals(0.0f, minResult.getValue(), DELTA); AnalysisContext maxContext = createContext(35.0f); AlgorithmResult maxResult = strategy.process(maxContext); assertEquals(0.0f, maxResult.getValue(), DELTA); }
    @Test void process_shouldThrowException_whenContextOrMeasurementIsNull() { assertThrows(IllegalArgumentException.class, () -> strategy.process(null)); assertThrows(IllegalArgumentException.class, () -> strategy.process(new AnalysisContext(null, List.of(), 1, LocalDateTime.now()))); }
}
