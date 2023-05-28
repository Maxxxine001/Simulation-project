
package simulation.ships;

public abstract class Ship {
    protected static int nextId = 0;
    protected int size;
    protected int id;

    public Ship() {
        this.id = nextId;
        nextId++;
    }
    
    public abstract float Rent();
}
