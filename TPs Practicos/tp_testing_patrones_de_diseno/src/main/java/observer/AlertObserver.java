package observer;

import service.AlertService;
import singleton.Logger;

// Observer that logs alerts
public class AlertObserver implements TransportObserver {

    private final Logger logger;
    private final AlertService alertService;

    // Inject dependencies
    public AlertObserver(AlertService alertService, Logger logger) {
        this.alertService = alertService;
        this.logger = logger;
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