import java.awt.*;

public class InterfazGrafica {
    private TransporteStrategy strategyActual;

    public void setTransporte(TransporteStrategy nuevaStrategy){
        this.strategyActual = nuevaStrategy;
        System.out.println("Cambiaste el vehiculo");
    }

    public void mostrarDatosViaje() {
        if (strategyActual != null) {
            System.out.println("---------------------------\n");
            System.out.println("Transporte: " + strategyActual.getName());
            System.out.println("Precio: $" + Math.round(strategyActual.getCost()));
            System.out.println("Distancia: " + strategyActual.getDistance() + " km");
            System.out.println("Tiempo estimado (ETA): " + strategyActual.getETA() + " minutos");
            System.out.println("--------------------------\n");
        }
        else {
            System.out.println("¡Error! Primero debes elegir un medio de transporte.");
        }
    }
}
