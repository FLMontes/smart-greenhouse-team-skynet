package strategy;

import java.util.Random;

public class BikeStrategy implements TransportStrategy{
    private final Random rand = new Random();

    @Override
    public String getName(){
        return "Bicicleta";
    }

    @Override
    public double getCost() {
        return 10 + (rand.nextDouble() * 40.0);
    }

    @Override
    public double getDistance() {
        return 5.5;
    }

    @Override
    public int getETA() {
        return 40 + rand.nextInt(6);
    }
}
