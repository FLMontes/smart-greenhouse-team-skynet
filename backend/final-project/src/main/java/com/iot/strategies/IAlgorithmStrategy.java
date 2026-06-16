package com.iot.strategies;

import com.iot.models.dto.AnalysisContext;
import com.iot.models.dto.AlgorithmResult;

/**
 * Interface for the Strategy pattern.
 * Defines the contract for all algorithms that process environmental measurements.
 */
public interface IAlgorithmStrategy {

    /**
     * Processes an analysis context (averaged/historical data) to evaluate specific conditions.
     * @param context The context containing averaged and historical measurements.
     * @return The specific result of the algorithm.
     */
    AlgorithmResult process(AnalysisContext context);

}
