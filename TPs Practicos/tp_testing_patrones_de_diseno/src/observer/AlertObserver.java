package observer;

import singleton.Logger;
import service.AlertService;

public class AlertObserver implements TransportObserver {
    private final Logger logger = Logger.getInstance();
    //private final double maxCost; --> Ya no va mas porque los umbrales están dentro de AlertService, no dentro del AlertObserver
    //private final int maxEta;     --> Ya no va mas porque los umbrales están dentro de AlertService, no dentro del AlertObserver

    private AlertService alertService;

    public AlertObserver(AlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public void update(TransportSnapshot snapshot) {

        if(alertService.shouldAlertCost(snapshot.cost())){
            logger.logWarning("Costo demasiado alto: $" + Math.round(snapshot.cost()));
        }
        if(alertService.shouldAlertETA(snapshot.eta())) {
            logger.logError("ETA demasiado largo: " + snapshot.eta() + " minutos");
        }
    }
}
