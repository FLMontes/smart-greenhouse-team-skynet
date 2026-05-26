package logger;

import singleton.ILogger; // Asegurate de que este import coincida con donde guardaste la interfaz
import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {

    private List<String> mensajesGuardados = new ArrayList<>();

    @Override
    public void logWarning(String message) {
        mensajesGuardados.add(message);
    }

    // AGREGAMOS LOS 3 MÉTODOS QUE FALTABAN:
    @Override
    public void logDebug(String message) {
        mensajesGuardados.add(message);
    }

    @Override
    public void logInfo(String message) {
        mensajesGuardados.add(message);
    }

    @Override
    public void logError(String message) {
        mensajesGuardados.add(message);
    }

    public List<String> getMensajesGuardados() {
        return mensajesGuardados;
    }

    public void limpiarMensajes() {
        mensajesGuardados.clear();
    }
}