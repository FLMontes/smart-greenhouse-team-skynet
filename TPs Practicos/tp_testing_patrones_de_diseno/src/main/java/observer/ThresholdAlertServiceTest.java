import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThresholdAlertServiceTest {

    private ThresholdAlertService alertService;

    // Definimos umbrales fijos para los tests
    private final double UMBRAL_COSTO = 500.0;
    private final int UMBRAL_ETA = 30;

    @BeforeEach
    public void setUp() {
        // Instanciamos la clase pura antes de cada test
        this.alertService = new ThresholdAlertService(UMBRAL_COSTO, UMBRAL_ETA);
    }

    // 1. Costo por debajo del umbral -> shouldAlertCost retorna false
    @Test
    public void testShouldAlertCost_CuandoEstaPorDebajoDelUmbral_RetornaFalse() {
        boolean resultado = alertService.shouldAlertCost(499.9);
        assertFalse(resultado, "El costo está por debajo del umbral, no debería alertar.");
    }

    // 2. Costo exactamente en el umbral -> DEFINIDO: retorna true (Documentado en Javadoc)
    /**
     * Comportamiento definido por el equipo:
     * Cuando el costo es exactamente igual al umbral límite establecido,
     * el sistema DEBE retornar true y disparar la alerta preventivamente.
     */
    @Test
    public void testShouldAlertCost_CuandoEstaExactamenteEnElUmbral_RetornaTrue() {
        boolean resultado = alertService.shouldAlertCost(UMBRAL_COSTO);
        assertTrue(resultado, "El costo es exactamente el umbral, se definió que SÍ debe alertar.");
    }

    // 3. Costo por encima del umbral -> shouldAlertCost retorna true
    @Test
    public void testShouldAlertCost_CuandoEstaPorEncimaDelUmbral_RetornaTrue() {
        boolean resultado = alertService.shouldAlertCost(500.1);
        assertTrue(resultado, "El costo supera el umbral, debe alertar.");
    }

    // 4. ETA por debajo del umbral -> shouldAlertETA retorna false
    @Test
    public void testShouldAlertETA_CuandoEstaPorDebajoDelUmbral_RetornaFalse() {
        boolean resultado = alertService.shouldAlertETA(29);
        assertFalse(resultado, "El ETA está por debajo del umbral, no debería alertar.");
    }

    // 5. ETA por encima del umbral -> shouldAlertETA retorna true
    @Test
    public void testShouldAlertETA_CuandoEstaPorEncimaDelUmbral_RetornaTrue() {
        boolean resultado = alertService.shouldAlertETA(31);
        assertTrue(resultado, "El ETA supera el umbral, debe alertar.");
    }
}