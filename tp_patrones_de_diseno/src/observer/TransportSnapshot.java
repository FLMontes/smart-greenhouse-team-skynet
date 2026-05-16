package observer;

public class TransportSnapshot {
    private final String transportName;
    private final double cost;
    private final double distance;
    private final int eta;

    public TransportSnapshot(String transportName, double cost, double distance, int eta) {
        this.transportName = transportName;
        this.cost = cost;
        this.distance = distance;
        this.eta = eta;
    }
    
    public String getTransportName() {
        return transportName;
    }

    public double getCost() {
        return cost;
    }

    public double getDistance() {
        return distance;
    }

    public int getETA() {
        return eta;
    }
}