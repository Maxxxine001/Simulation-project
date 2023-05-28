
package simulation.ships;
import java.util.Random;


public class Civil extends Ship{
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 70;
    private static final int HAULING_PRICE = 200;
    private static final int MAX_RESIDENCE_TIME = 45;
    private static final int MIN_RESIDENCE_TIME = 1;
    private static final int RESIDENCE_PRICE_PER_DAY = 20;
    

    
    public Civil() {
        Random random = new Random();
        this.ResidenceDays = random.nextInt(MAX_RESIDENCE_TIME-MIN_RESIDENCE_TIME)+MIN_RESIDENCE_TIME;
        this.size = random.nextInt(MAX_SIZE-MIN_SIZE)+MIN_SIZE;
    }
    
    @Override
    public int Rent() {
        return HAULING_PRICE * 2 + ResidenceDays * RESIDENCE_PRICE_PER_DAY;
    }
        
}
