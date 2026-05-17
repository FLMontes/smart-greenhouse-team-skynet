package strategy;
import java.util.Random;
public class Colectivo implements TransportStrategy{
    private Random rand = new Random();

    @Override
    public String getName(){
        return "Colectivo";
    }

    @Override
    public double getCost() {
        return 700.0 + (rand.nextDouble() * 100.0);
    }

    @Override
    public double getDistance() {
        return 5.5 + (rand.nextDouble() * 0.5);
    }

    @Override
    public int getETA() {
        return 30 + rand.nextInt(21);
    }
}