
package simulation.ships;
import java.util.Random;


public class Civil extends Ship{
    
    private static final int HAULING_PRICE = 200;
    private static final int MAX_RESIDENCE_TIME = 100;
    private static final int MIN_RESIDENCE_TIME = 1;
    private static final int RESIDENCE_PRICE_PER_DAY = 20;
    
    public int residenceDays;
    
    public Civil() {
        super();
        Random random = new Random();
        this.residenceDays = random.nextInt(MIN_RESIDENCE_TIME, MAX_RESIDENCE_TIME);
    }
    
    @Override
    public float Rent() {
        return HAULING_PRICE * 2 + residenceDays * RESIDENCE_PRICE_PER_DAY;
    }
        
}
