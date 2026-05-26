import singleton.Logger;
import strategy.*;
import observer.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.logInfo("Inicio de la aplicacion");

        TransportStrategy taxi = new Taxi();
        TransportStrategy bus = new Colectivo();
        TransportStrategy bici = new Bicicleta();

        TransportContext context = new TransportContext();

        TransportMonitor monitor = new TransportMonitor(context);

        ConsolePrinter consolePrinter = new ConsolePrinter();
        AlertService alertService = new ThresholdAlertService(5000, 30);
        AlertObserver alertObserver = new AlertObserver(alertService, logger);

        monitor.addObserver(consolePrinter);
        monitor.addObserver(alertObserver);

        context.setTransport(taxi);

        monitor.start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.logInfo("1 - Taxi");
            logger.logInfo("2 - Bus");
            logger.logInfo("3 - Bicicleta");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    context.setTransport(taxi);
                    break;

                case 2:
                    context.setTransport(bus);
                    break;

                case 3:
                    context.setTransport(bici);
                    break;

                default:
                    logger.logWarning("Opcion invalida");
                
                case 0:
                    monitor.stop();
                    System.out.println("Fin de la aplicacion");
                    return;
            }
        }
    }
}