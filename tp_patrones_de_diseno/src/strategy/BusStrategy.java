import java.util.Random;
public class Colectivo implements TransporteStrategy{
    private Random rand = new Random();

    @Override
    public String getName(){
        return "Colectivo";
    }

    @Override
    public double getCost() {
        return 500.0 + (rand.nextDouble()*500);
    }

    @Override
    public double getDistance() {
        return 5.5;
    }

    @Override
    public int getETA() {
        return 30 + rand.nextInt(21);
    }
}
