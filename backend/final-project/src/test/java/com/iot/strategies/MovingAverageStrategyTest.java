package com.iot.strategies;

import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovingAverageStrategyTest {
    private static final double DELTA = 0.0001;
    private MovingAverageStrategy strategy;
    @BeforeEach void setUp() { strategy = new MovingAverageStrategy(); }
    private AnalysisContext createContext(List<Float> temperatureWindow) { List<Measurement> sourceMeasurements = new ArrayList<>(); for (Float temp : temperatureWindow) { Measurement m = new Measurement(); m.setTemperature(temp); sourceMeasurements.add(m); } return new AnalysisContext(new Measurement(), sourceMeasurements, 1, LocalDateTime.now()); }
    @Test void process_shouldCalculateAverageCorrectly_withMultipleValues() { AnalysisContext context = createContext(List.of(20.0f, 22.0f, 24.0f)); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(22.0, result.getValue(), DELTA); assertEquals("averageTemperature", result.getOutputName()); }
    @Test void process_shouldReturnZero_whenHistoryIsEmpty() { AnalysisContext context = createContext(Collections.emptyList()); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0, result.getValue(), DELTA); }
    @Test void process_shouldCalculateAverage_withSingleValueInHistory() { AnalysisContext context = createContext(List.of(30.5f)); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(30.5, result.getValue(), DELTA); }
    @Test void process_shouldThrowException_whenContextOrHistoryIsNull() { assertThrows(IllegalArgumentException.class, () -> strategy.process(null)); assertThrows(IllegalArgumentException.class, () -> strategy.process(new AnalysisContext(new Measurement(), null, 1, LocalDateTime.now()))); }
}
