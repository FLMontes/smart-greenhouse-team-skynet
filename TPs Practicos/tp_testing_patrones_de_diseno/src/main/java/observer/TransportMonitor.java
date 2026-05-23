package observer;

import java.util.ArrayList;
import java.util.List;

import strategy.TransportContext;
import strategy.TransportStrategy;

public class TransportMonitor {
    private final List<TransportObserver> observers;
    private final TransportContext context;
    private boolean running;

    public TransportMonitor(TransportContext context) {
        this.context = context;
        observers = new ArrayList<>();
    }

    public void addObserver(TransportObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(TransportSnapshot snapshot) {
        for(TransportObserver observer : observers) {
            observer.update(snapshot);
        }
    }

    public void updateTransport() {
        TransportStrategy strategy = context.getStrategy();
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

    public void startMonitoring(int intervalMs) {
        running = true;

        new Thread(() -> {
                            while(running) {
                                updateTransport();
                                try {Thread.sleep(intervalMs);} 
                                catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    running = false;
                                }
                            }
                         }).start();
    }    
    
    public void stop() {
        running = false; 
    }

    public void start() {
        TransportStrategy strategy = context.getStrategy();

        TransportSnapshot snapshot = new TransportSnapshot(strategy.getName(),
                                                           strategy.getCost(),
                                                           strategy.getDistance(),
                                                           strategy.getETA());
        notifyObservers(snapshot);
    }
}