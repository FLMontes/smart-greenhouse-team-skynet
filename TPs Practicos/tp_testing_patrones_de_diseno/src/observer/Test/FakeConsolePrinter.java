package observer.Test;

import observer.ConsolePrinter;
import observer.TransportSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FakeConsolePrinter extends ConsolePrinter {

    private List<TransportSnapshot> snapshotsGuardados = new ArrayList<>();

    @Override
    public void update(TransportSnapshot snapshot) {
        snapshotsGuardados.add(snapshot);
    }

    public List<TransportSnapshot> getSnapshotsGuardados() {
        return snapshotsGuardados;
    }

    public void limpiarSnapshots() {
        snapshotsGuardados.clear();
    }
}