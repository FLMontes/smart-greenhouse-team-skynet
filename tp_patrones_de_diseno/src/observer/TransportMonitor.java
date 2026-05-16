package observer;

import java.util.ArrayList;
import java.util.List;

import strategy.TransportStrategy;

public class TransportMonitor {
    private final List<TransportObserver> observers;
    private TransportStrategy strategy;

    public TransportMonitor() {
        observers = new ArrayList<>();
    }

    public void addObserver(TransportObserver observer) {
        observers.add(observer);
    }

    public void setStrategy(TransportStrategy strategy) {
        this.strategy = strategy;
    }

    public void notifyObservers(TransportSnapshot snapshot) {
        for(TransportObserver observer : observers) {
            observer.update(snapshot);
        }
    }

    public void updateTransport() {
        if(strategy == null) {
            System.out.println("[ERROR] No hay transporte seleccionado");
            return;
        }
        TransportSnapshot snapshot = new TransportSnapshot(strategy.getName(), 
                                                           strategy.getCost(),
                                                           strategy.getDistance(),
                                                           strategy.getETA());
        notifyObservers(snapshot);
    }
}