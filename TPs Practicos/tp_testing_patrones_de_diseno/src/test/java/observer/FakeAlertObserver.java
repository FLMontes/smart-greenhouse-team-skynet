package observer;

import logger.FakeLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FakeAlertService;

import static org.junit.jupiter.api.Assertions.*;

public class FakeAlertObserver {

    private FakeLogger fakeLogger;

    @BeforeEach
    public void setUp() {
        fakeLogger = new FakeLogger();
        fakeLogger.limpiarMensajes();
    }

    @Test
    public void testAlertObserverLoggeaCuandoNotifica() {
        FakeAlertService alwaysAlertService = new FakeAlertService(true, true);
        AlertObserver observer = new AlertObserver(alwaysAlertService, fakeLogger);
        TransportSnapshot snapshot = new TransportSnapshot("taxi", 100.0, 50.0, 20);

        observer.update(snapshot);

        assertTrue(fakeLogger.getMensajesGuardados().size() > 0);
    }

    @Test
    public void testAlertObserverNoLoggeaNada() {
        FakeAlertService neverAlertService = new FakeAlertService(false, false);
        AlertObserver observer = new AlertObserver(neverAlertService, fakeLogger);
        TransportSnapshot snapshot = new TransportSnapshot("uber", 5.0, 20.0, 5);

        observer.update(snapshot);

        assertEquals(0, fakeLogger.getMensajesGuardados().size());
    }
}