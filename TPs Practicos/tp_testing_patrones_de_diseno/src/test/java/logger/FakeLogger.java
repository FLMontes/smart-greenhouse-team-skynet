package observer;

import singleton.Logger;
import java.util.ArrayList;
import java.util.List;

public class FakeLogger extends Logger {

    private List<String> mensajesGuardados = new ArrayList<>();

    @Override
    public void logWarning(String message) {
        mensajesGuardados.add(message);
    }

    public List<String> getMensajesGuardados() {
        return mensajesGuardados;
    }

    public void limpiarMensajes() {
        mensajesGuardados.clear();
    }
}