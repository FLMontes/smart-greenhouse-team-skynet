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


class HumidityStrategyTest {
    private static final float DELTA = 0.0001f;
    private HumidityStrategy strategy;
    @BeforeEach void setUp() { strategy = new HumidityStrategy(); strategy.setMinHumidity(40.0f); }
    private AnalysisContext createContext(float humidityValue) { Measurement m = new Measurement(); m.setHumidity(humidityValue); return new AnalysisContext(m, List.of(m), 1, LocalDateTime.now()); }
    @Test void process_shouldReturnWateringRequired_whenHumidityIsBelowMin() { AnalysisContext context = createContext(30.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(1.0f, result.getValue(), DELTA); assertEquals("wateringRequired", result.getOutputName()); }
    @Test void process_shouldReturnNormal_whenHumidityIsAboveMin() { AnalysisContext context = createContext(60.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0f, result.getValue(), DELTA); }
    @Test void process_shouldReturnNormal_whenHumidityIsExactlyAtMin() { AnalysisContext context = createContext(40.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0f, result.getValue(), DELTA); }
    @Test void shouldUseDefaultMinimumHumidity_whenInstantiatedWithDefaultConstructor() { HumidityStrategy defaultStrategy = new HumidityStrategy(); assertEquals(40.0f, defaultStrategy.getMinHumidity(), DELTA); }
    @Test void shouldUpdateMinimumHumidity_andUseItInLogic() { strategy.setMinHumidity(50.0f); AnalysisContext context = createContext(45.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(1.0f, result.getValue(), DELTA); }
    @Test void process_shouldThrowException_whenContextOrMeasurementIsNull() { assertThrows(IllegalArgumentException.class, () -> strategy.process(null)); assertThrows(IllegalArgumentException.class, () -> strategy.process(new AnalysisContext(null, List.of(), 1, LocalDateTime.now()))); }
}
