import java.util.Random;

/**
 *
 */
public class ZMath {
    public int groupSize = 10;
    public int maxGroupSize = 15;
    public int startFood = 15;
    public double foodLimit = 10;
    private int lifeSpan = 200;
    double foodPerPerson =  7;

    public int getLifeSpan() {
        return lifeSpan;
    }


    public double deltaFood() {
        double deltaFood = foodPerPerson * new Random().nextDouble() * 4 - 20;

        return deltaFood;
    }

}