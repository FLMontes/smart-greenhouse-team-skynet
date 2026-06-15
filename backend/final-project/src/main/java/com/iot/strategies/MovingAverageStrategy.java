package com.iot.strategies;

import org.springframework.stereotype.Component;
import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;
import com.iot.models.entities.Measurement;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MovingAverageStrategy implements IAlgorithmStrategy {

    @Override
    public AlgorithmResult process(AnalysisContext context) {
        if (context == null || context.getSourceMeasurements() == null) {
            throw new IllegalArgumentException("AnalysisContext must not be null");
        }

        List<Measurement> history = context.getSourceMeasurements();
        double sum = 0;

        // Calculamos el promedio de temperatura basado en las mediciones de la base de datos
        for (Measurement m : history) {
            sum += m.getTemperature();
        }

        float average = history.isEmpty() ? 0.0f : (float) (sum / history.size());

        return new AlgorithmResult(
                "MovingAverageStrategy",
                "Calculates the moving average of temperature using a sliding window.",
                "PostgreSQL measurements table",
                "Last " + history.size() + " stored measurements",
                "averageTemperature",
                average,
                "°C",
                LocalDateTime.now()
        );
    }
}