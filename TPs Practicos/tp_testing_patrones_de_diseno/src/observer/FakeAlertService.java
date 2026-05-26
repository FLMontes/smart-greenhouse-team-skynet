package observer;

// Fake service for testing
public class FakeAlertService implements AlertService {

    private final boolean costAlert;
    private final boolean etaAlert;

    // Sets fake responses
    public FakeAlertService(boolean costAlert, boolean etaAlert) {
        this.costAlert = costAlert;
        this.etaAlert = etaAlert;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        // Returns predefined value
        return costAlert;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        // Returns predefined value
        return etaAlert;
    }
}