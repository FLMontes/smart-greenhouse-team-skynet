package observer;

import singleton.Logger;

public class ConsolePrinter implements TransportObserver {
    private final Logger logger = Logger.getInstance();

    @Override
    public void update(TransportSnapshot snapshot) {
        logger.logInfo("-----------------------------");
        logger.logInfo("Transporte: " + snapshot.getTransportName());
        logger.logInfo("Costo: $" + Math.round(snapshot.getCost()));
        logger.logInfo("Distancia: " + Math.round(snapshot.getDistance()) + " km");
        logger.logInfo("ETA: " + snapshot.getETA() + " minutos");
        logger.logInfo("-----------------------------");
    }
}