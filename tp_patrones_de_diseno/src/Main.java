import observer.*;
import strategy.*;

public class Main {
    public static void main(String[] args) {

        TransportMonitor monitor = new TransportMonitor();

        ConsolePrinter printer = new ConsolePrinter();

        AlertObserver alert = new AlertObserver(3000, 35);

        monitor.addObserver(printer);
        monitor.addObserver(alert);

        TransportStrategy taxi = new TaxiStrategy();

        TransportStrategy bus = new BusStrategy();

        TransportStrategy bike = new BikeStrategy();

        monitor.setStrategy(taxi);
        monitor.updateTransport();

        monitor.setStrategy(bus);
        monitor.updateTransport();

        monitor.setStrategy(bike);
        monitor.updateTransport();
    }
}