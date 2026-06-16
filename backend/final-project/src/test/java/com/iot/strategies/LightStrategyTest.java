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

class LightStrategyTest {
    private static final float DELTA = 0.0001f;
    private LightStrategy strategy;
    @BeforeEach void setUp() { strategy = new LightStrategy(); strategy.setTargetLightLevel(1000.0f); }
    private AnalysisContext createContext(float lightValue) { Measurement m = new Measurement(); m.setLight(lightValue); return new AnalysisContext(m, List.of(m), 1, LocalDateTime.now()); }
    @Test void process_shouldReturnCorrectDeficit_whenLightIsBelowTarget() { AnalysisContext context = createContext(700.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(300.0f, result.getValue(), DELTA); assertEquals("lightDeficit", result.getOutputName()); }
    @Test void process_shouldReturnZeroDeficit_whenLightIsAboveTarget() { AnalysisContext context = createContext(1500.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0f, result.getValue(), DELTA); }
    @Test void process_shouldReturnZeroDeficit_whenLightIsExactlyAtTarget() { AnalysisContext context = createContext(1000.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(0.0f, result.getValue(), DELTA); }
    @Test void shouldUseDefaultTargetLight_whenInstantiatedWithDefaultConstructor() { LightStrategy defaultStrategy = new LightStrategy(); assertEquals(500.0f, defaultStrategy.getTargetLightLevel(), DELTA); }
    @Test void shouldUpdateTargetLightLevel_andUseItInLogic() { strategy.setTargetLightLevel(2000.0f); AnalysisContext context = createContext(1200.0f); AlgorithmResult result = strategy.process(context); assertNotNull(result); assertEquals(800.0f, result.getValue(), DELTA); }
    @Test void process_shouldThrowException_whenContextOrMeasurementIsNull() { assertThrows(IllegalArgumentException.class, () -> strategy.process(null)); assertThrows(IllegalArgumentException.class, () -> strategy.process(new AnalysisContext(null, List.of(), 1, LocalDateTime.now()))); }
}
