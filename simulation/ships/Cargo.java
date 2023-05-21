
package simulation.ships;
import java.util.Random;


public class Cargo extends Ship{
    
    private static final int MIN_TONNAGE = 2000;
    private static final int MAX_TONNAGE = 25000;
    private static final int DOCKING_PRICE_PER_DAY = 3000;
    private static final int UNLOADING_POWER_PER_DAY = 3500;
    private static final int LOADING_POWER_PER_DAY = 5000;
    
    private int tonnage;
    
    
    public Cargo(){
        super(id);
        Random random = new Random();
        this.tonnage = random.nextInt(MIN_TONNAGE, MAX_TONNAGE);
    }
    
    
    protected float UnloadingDays(){
        return tonnage / UNLOADING_POWER_PER_DAY;
    }
    
    protected float LoadingDays(){
        return tonnage / LOADING_POWER_PER_DAY;
    }
    
    protected int DaysInDock(){
        return (int)Math.ceil(this.UnloadingDays() + this.LoadingDays());  
    }
    
    @Override
    protected float Rent(){
        return (this.UnloadingDays() + this.LoadingDays()) * DOCKING_PRICE_PER_DAY + tonnage;
    }
    
    
}
