import java.util.Random;
public class Taxi implements TransporteStrategy{
    private Random rand = new Random();

    @Override
    public String getName(){
        return "Taxi";
    }

    @Override
    public double getCost() {
        return 1500.0 + (rand.nextDouble()*2000);
    }

    @Override
    public double getDistance() {
        return 5.5;
    }

    @Override
    public int getETA() {
        return 10 + rand.nextInt(16);
    }
}
