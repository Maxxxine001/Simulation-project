
package simulation.ships;

public abstract class Ship {
    protected static int nextId = 1000;
    protected int size;
    protected int id;
    public int ResidenceDays;

    public Ship() {
        this.id = nextId;
        nextId++;
    }
    public abstract int Rent();
}
