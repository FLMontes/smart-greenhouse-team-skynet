import java.util.ArrayList;
import java.util.List;

/**
 * Core class responsible for analyzing environmental measurements.
 * Acts as the Subject in the Observer pattern and Context in the Strategy pattern.
 */
public class EnvironmentalAnalyzer {

    private Measurement currentMeasurement;
    private List<IAlgorithmStrategy> strategies;
    private List<IObserver> observers;
    private IMeasurementRepository repository;

    /**
     * Constructor initializes the lists to avoid NullPointerExceptions.
     */
    public EnvironmentalAnalyzer() {
        this.strategies = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Attaches an observer to the analyzer.
     *
     * @param o The observer to attach.
     */
    public void attach(IObserver o) {
        this.observers.add(o);
    }

    /**
     * Detaches an observer from the analyzer.
     *
     * @param o The observer to detach.
     */
    public void detach(IObserver o) {
        this.observers.remove(o);
    }

    /**
     * Notifies all attached observers about an update.
     */
    public void notifyObservers() {
        for (IObserver observer : this.observers) {
            observer.update();
        }
    }

    /**
     * Analyzes a new measurement by processing it through all configured strategies
     * and then notifying the observers.
     *
     * @param m The measurement to analyze.
     */
    public void analyzeMeasurement(Measurement m) {
        this.currentMeasurement = m;

        // 1. Process the measurement using all defined algorithm strategies
        if (this.strategies != null) {
            for (IAlgorithmStrategy strategy : this.strategies) {
                strategy.process(m);
            }
        }

        // 2. Notify observers (like HardwareAlarmObserver or WebDashboardObserver)
        notifyObservers();
    }

    /**
     * Sets the algorithm strategies to be used.
     *
     * @param strategies List of algorithm strategies.
     */
    public void setStrategies(List<IAlgorithmStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * Sets the measurement repository.
     *
     * @param repository The repository implementation.
     */
    public void setRepository(IMeasurementRepository repository) {
        this.repository = repository;
    }
}