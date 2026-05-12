
public interface TransporteStrategy {
    String getName();  //Nombre del transporte
    double getCost();  //Costo del viaje
    double getDistance(); //Distancia
    int getETA();  //Tiempo de arribo
}
