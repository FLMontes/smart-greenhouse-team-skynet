<<<<<<< HEAD:TPs Practicos/tp_testing_patrones_de_diseno/src/main/java/service/ThresholdAlertService.java
package service;

=======
package observer;

// Alert implementation using thresholds
public class ThresholdAlertService implements AlertService {

    private final double maxCost;
    private final int maxEta;

    // Sets max allowed values
    public ThresholdAlertService(double maxCost, int maxEta) {
        this.maxCost = maxCost;
        this.maxEta = maxEta;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        // Returns true if cost exceeds limit
        return cost > maxCost;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        // Returns true if ETA exceeds limit
        return eta > maxEta;
    }
}





/*
>>>>>>> testing-franco:TPs Practicos/tp_testing_patrones_de_diseno/src/observer/ThresholdAlertService.java
// Implementación concreta

public class ThresholdAlertService implements AlertService {
    public ThresholdAlertService(double maxCost, int maxEta) { ... }
...
} */