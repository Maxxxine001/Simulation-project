
package simulation.ships;
import java.util.Random;


public class Lifeboat extends Ship{

    private static final int LIFEBOATSIZE = 15;
    private static final int RENTCOST = 50;
    
    public Lifeboat(int Days){
        this.ResidenceDays=Days+1;
        this.size=LIFEBOATSIZE;
    }

    @Override
    public int Rent() {
        return RENTCOST*this.ResidenceDays;
    }


    public static int InterveningCost(Ship x) {
        if (x instanceof Cargo) return 100;
        else return 30;
    }
}
