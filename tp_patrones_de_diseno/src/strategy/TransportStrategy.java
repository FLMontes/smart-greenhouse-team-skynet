package strategy;

public interface TransportStrategy {
    String getName();  //Nombre del Transporte
    double getCost();  //Costo del viaje
    double getDistance(); //Distancia
    int getETA();  //Tiempo de arribo
}