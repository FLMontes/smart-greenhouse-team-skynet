package strategy;

public class TransportContext{
    private TransportStrategy strategyActual;

    public void setTransport(TransportStrategy nuevaStrategy){
        this.strategyActual = nuevaStrategy;
        System.out.println("Transporte cambiado a " + nuevaStrategy.getName());
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
            System.out.println("¡Error! Primero debes elegir un medio de Transporte.");
        }
    }

    public TransportStrategy getStrategy() {
        return strategyActual;
    }
}