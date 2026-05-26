package service;

// Defines alert rules
public interface AlertService {

    // Checks cost threshold
    boolean shouldAlertCost(double cost);

    // Checks ETA threshold
    boolean shouldAlertETA(int eta);
}
