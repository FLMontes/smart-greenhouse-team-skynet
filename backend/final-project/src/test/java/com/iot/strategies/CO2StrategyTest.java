package com.iot.strategies;

import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import com.iot.models.entities.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CO2StrategyTest {

    private CO2Strategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CO2Strategy();
    }

    private AnalysisContext createContext(float co2Value) {
        Measurement m = new Measurement();
        m.setCo2(co2Value);
        // Creamos un contexto simulado con la medición promediada
        return new AnalysisContext(m, List.of(m), 1, LocalDateTime.now());
    }

    @Test
    void process_shouldReturnVentilationRequired_whenCO2IsAboveMax() {
        // Ejecutamos la estrategia con 1500 de CO2 (el umbral por defecto es 1000)
        AlgorithmResult result = strategy.process(createContext(1500.0f));

        // Verificamos que devuelva 1.0f (Alarma encendida)
        assertEquals(1.0f, result.getValue());
    }

    @Test
    void process_shouldReturnNormal_whenCO2IsBelowMax() {
        // Ejecutamos la estrategia con 800 de CO2
        AlgorithmResult result = strategy.process(createContext(800.0f));

        // Verificamos que devuelva 0.0f (Normal)
        assertEquals(0.0f, result.getValue());
    }
}
