package observer;

import org.junit.jupiter.api.Test;
import service.AlertService;
import singleton.Logger;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AlertObserverMockTest {

    @Test
    void shouldLogWarningWhenCostIsHigh() {

        // Crear mocks
        AlertService alertService = mock(AlertService.class);
        Logger logger = mock(Logger.class);

        // Configurar el comportamiento del mock
        when(alertService.shouldAlertCost(anyDouble())).thenReturn(true);

        // Crear observer
        AlertObserver observer = new AlertObserver(alertService, logger);

        // Crear snapshot
        TransportSnapshot snapshot = new TransportSnapshot("Uber",1500,12,15);

        // Ejecutar update
        observer.update(snapshot);

        // Verificar interacción
        verify(logger).logWarning(anyString());
    }

    @Test
    void shouldLogErrorWhenETAIsHigh() {

        // Crear mocks
        AlertService alertService = mock(AlertService.class);
        Logger logger = mock(Logger.class);

        // Configurar el comportamiento del mock
        when(alertService.shouldAlertETA(anyInt())).thenReturn(true);

        // Crear observer
        AlertObserver observer = new AlertObserver(alertService, logger);

        // Crear snapshot
        TransportSnapshot snapshot = new TransportSnapshot("Bus",500,5,60);

        // Ejecutar update
        observer.update(snapshot);

        // Verificar interacción
        verify(logger).logError(anyString());
    }

    @Test
    void shouldNotLogAnythingWhenAlertsAreFalse() {

        // Crear mocks
        AlertService alertService = mock(AlertService.class);
        Logger logger = mock(Logger.class);

        // Configurar el comportamiento del mock
        when(alertService.shouldAlertCost(anyDouble())).thenReturn(false);

        when(alertService.shouldAlertETA(anyInt())).thenReturn(false);

        // Crear observer
        AlertObserver observer = new AlertObserver(alertService, logger);

        // Crear snapshot
        TransportSnapshot snapshot = new TransportSnapshot("Train",200,20,10);

        // Ejecutar update
        observer.update(snapshot);

        // Verificar que el logger no fue usado
        verify(logger, never()).logWarning(anyString());
        verify(logger, never()).logError(anyString());
    }
}