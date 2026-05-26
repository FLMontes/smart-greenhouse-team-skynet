package observer;

public interface AlertService {

    boolean shouldAlertCost(double cost);

    boolean shouldAlertETA(int eta);
}