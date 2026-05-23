package strategy;
import java.util.Random;
public class Bicicleta implements TransportStrategy{
    private Random rand = new Random();

    @Override
    public String getName(){
        return "Bicicleta";
    }

    @Override
    public double getCost() {
        return rand.nextDouble() * 50.0;
    }

    @Override
    public double getDistance() {
        return 4.0 + (rand.nextDouble() * 1.5);
    }

    @Override
    public int getETA() {
        return 20 + rand.nextInt(16);
    }
}