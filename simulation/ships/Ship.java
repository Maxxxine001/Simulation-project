
package simulation.ships;

public class Ship {
    protected static int idCount=1000;
    protected int size;
    protected int id;

    public Ship(){
        this.id = idCount;
        idCount+=1;
    }
    
    protected abstract float Rent();
}
