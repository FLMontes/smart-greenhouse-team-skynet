package observer;

public class ThresholdAlertService implements AlertService {

    private double maxCost;
    private int maxEta;

    public ThresholdAlertService(double maxCost, int maxEta) {
        this.maxCost = maxCost;
        this.maxEta = maxEta;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        return cost > maxCost;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        return eta > maxEta;
    }
}