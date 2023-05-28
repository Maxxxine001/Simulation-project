
package simulation.ships;

public abstract class Ship {
    protected static int idCount=1000;
    protected int size;
    protected int id;

    public Ship(){
        this.id = idCount;
        idCount+=1;
    }
    
    public abstract float Rent();{

    }
}
