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
// Implementación concreta

public class ThresholdAlertService implements AlertService {
    public ThresholdAlertService(double maxCost, int maxEta) { ... }
...
} */