
package simulation.ships;
import java.util.Random;


public class Civil extends Ship{
    //Konstruktor domyślny, generujący wartości atrybutów statku z wyznaczoncyh zakresów.
    public Civil() {
        Random random = new Random();
        this.ResidenceDays = random.nextInt(MAX_RESIDENCE_TIME - MIN_RESIDENCE_TIME + 1)+MIN_RESIDENCE_TIME;
        this.Size = random.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
    }

    //Metoda zwracająca koszt wynajmu miejsca w porcie, uwzględniający rozmiar statku oraz czas pobytu w porcie:
    @Override
    public int Rent() {
        return HAULING_PRICE_PER_SIZE * this.Size + this.ResidenceDays * RESIDENCE_PRICE_PER_DAY;
    }

    //Sekcja stałych definiujących parametry statku civil:

    private static final int MAX_SIZE = 70;//Maksymalny możliwy rozmiar statku.
    private static final int MIN_SIZE = 5;//Minimalny możliwy rozmiar statku.
    private static final int MAX_RESIDENCE_TIME = 15;//Maksymalny możliwy czas pobytu statku w porcie.
    private static final int MIN_RESIDENCE_TIME = 7;//Minimalny możliwy czas pobytu statku w porcie przez statek.
    private static final int HAULING_PRICE_PER_SIZE = 4;//Cena za każdą jednostke rozmiaru statku.
    private static final int RESIDENCE_PRICE_PER_DAY = 85;//Cena za każdy dzień pobytu statku w porcie.
}
