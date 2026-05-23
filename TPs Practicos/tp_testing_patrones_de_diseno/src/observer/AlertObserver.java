package observer;

import singleton.Logger;

// Observer that logs alerts
public class AlertObserver implements TransportObserver {

    private final Logger logger = Logger.getInstance();
    private final AlertService alertService;

    // Injects alert service
    public AlertObserver(AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public void update(TransportSnapshot snapshot) {

        // Checks cost alert
        if (alertService.shouldAlertCost(snapshot.getCost())) {
            logger.logWarning(
                    "High transport cost: $" + Math.round(snapshot.getCost())
            );
        }

        // Checks ETA alert
        if (alertService.shouldAlertETA(snapshot.getETA())) {
            logger.logError(
                    "High ETA: " + snapshot.getETA() + " minutes"
            );
        }
    }
}