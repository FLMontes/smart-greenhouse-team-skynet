package service;

public class FakeAlertService implements AlertService {

    private final boolean costAlert;
    private final boolean etaAlert;

    public FakeAlertService(boolean costAlert, boolean etaAlert) {
        this.costAlert = costAlert;
        this.etaAlert = etaAlert;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        return costAlert;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        return etaAlert;
    }
}