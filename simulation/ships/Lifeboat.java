package simulation.ships;

public class Lifeboat extends Ship{

    //Konstruktor wykorzystujący argument, w celu wyznaczenia czasu pobytu statku w porcie.
    public Lifeboat(int Days){
        this.ResidenceDays=Days+1;
        this.Size=LIFEBOAT_SIZE;
    }

    //Metoda zwracająca koszt najmu pojedyńczego statku.
    @Override
    public int Rent() {return RENT_COST_PER_DAY * this.ResidenceDays;}

    //Sekcja stałych definiujących parametry statku lifeboat:
    private static final int LIFEBOAT_SIZE = 15;//Rozmiar statku.
    private static final int RENT_COST_PER_DAY = 300;//Koszt najmu statku.
    private static final int INTERVENING_CARGO_COST = 800;//Koszt podjęcia interwencji dla statku typu Cargo.
    private static final int INTERVENING_CIVIL_COST = 125;//Koszt podjęcia interwencji dla statku typu Civil.

    //Dodatkowe metody:

    //Metoda zwracająca odpowiednią wartość w zależności od klasy pojazdu, który jest ratowany.
    //Wartość zwracana zawsze ujemna
    public static int InterveningCost(Ship x) {
        if (x instanceof Cargo) return INTERVENING_CARGO_COST;
        else return INTERVENING_CIVIL_COST;
    }
}
