
package simulation.ships;
import java.util.Random;


public class Cargo extends Ship{
    private static final int PRICE_FOR_SIZE = 2;
    private static final int MIN_TONNAGE = 2000;
    private static final int MAX_TONNAGE = 25000;
    private static final int DOCKING_PRICE_PER_DAY = 3000;
    private static final int UNLOADING_POWER_PER_DAY = 3500;
    private static final int LOADING_POWER_PER_DAY = 5000;
    
    private final int tonnage;
    protected float UnloadingDays(){
        return this.tonnage / UNLOADING_POWER_PER_DAY;}

    protected float LoadingDays(){
        return this.tonnage / LOADING_POWER_PER_DAY;}
    
    public Cargo(){
        Random random = new Random();
        this.tonnage = random.nextInt((MAX_TONNAGE-MIN_TONNAGE))+MAX_TONNAGE;
        this.ResidenceDays = (int) Math.ceil(UnloadingDays() + LoadingDays());
        this.size = 50+this.tonnage*2;
    }
    @Override
    public int Rent(){
        return this.ResidenceDays * DOCKING_PRICE_PER_DAY + this.size * PRICE_FOR_SIZE;}
}
