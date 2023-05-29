
package simulation.ships;

public abstract class Ship {

    //Konstruktor domyślny, przypisujący każdemu statku unikalne ID.
    protected Ship() {
        this.Id = NextId;
        NextId++;
    }

    //Abstrakcyjna metoda dziedziczona przez każdy obiekt pochodzenia Ship. Zwracająca odpowiednią wartość typu int, wykorzystywana w różnych celach.
    protected abstract int Rent();

    //Sekcja atrybutów każdego obiektu, dziedziczącego po klasie Ship:

    private static int NextId = 1000;//Zmienna statyczna umożliwiająca przypisywanie id konkretnym statkom.
    protected int Size;//Rozmiar statku
    protected int Id;//ID statku, pozwalające najskuteczniej rozróżnić jeden statek od drugiego.
    public int ResidenceDays;//Okres pobytu statku w porcie.
}
