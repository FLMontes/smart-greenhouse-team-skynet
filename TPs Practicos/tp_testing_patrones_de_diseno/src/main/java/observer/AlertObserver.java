package observer;

import singleton.Logger;

public class AlertObserver implements TransportObserver {
    private final Logger logger = Logger.getInstance();
    private final double maxCost;
    private final int maxEta;

    public AlertObserver(double maxCost, int maxEta) {
        this.maxCost = maxCost;
        this.maxEta = maxEta;
    }

    @Override
    public void update(TransportSnapshot snapshot) {
        if(snapshot.getCost() > maxCost){
            logger.logWarning("Costo demasiado alto: $" + Math.round(snapshot.getCost()));
        }
        if(snapshot.getETA() > maxEta) {
            logger.logWarning("ETA demasiado largo: " + snapshot.getETA() + " minutos");
        }
    }
}
