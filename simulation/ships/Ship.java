
package simulation.ships;

public abstract class Ship {
    
    protected int size;
    protected int id;
    
    public Ship(int size, int id){
        this.size = size;
        this.id = id;
    }
    
    protected abstract float Rent();
}
