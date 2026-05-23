public class ThresholdAlertService implements AlertService {
    private final double maxCost;
    private final int maxEta;

    public ThresholdAlertService(double maxCost, int maxEta) {
        this.maxCost = maxCost;
        this.maxEta = maxEta;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        // Comportamiento definido: Si es igual o mayor al umbral, alerta
        return cost >= this.maxCost;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        // Comportamiento definido: Si es igual o mayor al umbral, alerta
        return eta >= this.maxEta;
    }
}