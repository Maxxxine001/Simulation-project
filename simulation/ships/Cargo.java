
package simulation.ships;
import java.util.Random;


public class Cargo extends Ship{

    private int tonnage;//Dodatkowy atrybut. Wartość ładunku jaki dany statek może przewieźć.

    //Konstruktor domyślny, generujący wartości atrybutów statku z wyznaczoncyh zakresów.
    //Atrybut ResidenceDays jest tutaj liczony z wykorzystaniem czasu przeładunku tonażu.
    public Cargo(){
        Random random = new Random();
        this.tonnage = random.nextInt(MAX_TONNAGE - MIN_TONNAGE + 1) + MIN_TONNAGE;
        this.ResidenceDays = (int) Math.ceil(UnloadingDays() + LoadingDays());
        this.Size = 150+this.tonnage*2;
    }

    //Metoda zwracająca koszt wynajmu miejsca w porcie, uwzględniający rozmiar statku oraz czas pobytu w porcie:
    @Override
    public int Rent(){return this.ResidenceDays * DOCKING_PRICE_PER_DAY + this.Size * PRICE_FOR_SIZE;}

    //Sekcja stałych definiujących parametry statku cargo:

    private static final int MIN_TONNAGE = 2000;//Minimalny możliwy tonaż statku.
    private static final int MAX_TONNAGE = 8000;//Maksymalny możliwy tonaż statku.
    private static final int UNLOADING_POWER_PER_DAY = 3000;//Moc wyładunkowa tonażu statku.
    private static final int LOADING_POWER_PER_DAY = 4000;//Moc załadunkowa tonażu statku.
    private static final int PRICE_FOR_SIZE = 2;//Cena za każdą jednostkę rozmiaru statku.
    private static final int DOCKING_PRICE_PER_DAY = 150;//Cena za każdy dzień pobytu statku w porcie.

    //Dodatkowe metody:

    //Metoda zwracająca wartość zmiennoprzecinkową czasu trwania wyładunku w zależności od dostępnego tonażu:
    private float UnloadingDays(){return this.tonnage / UNLOADING_POWER_PER_DAY;}
    //Metoda zwracająca wartość zmiennoprzecinkową czasu trwania załadunku w zależności od dostępnego tonażu statku:
    private float LoadingDays(){return this.tonnage / LOADING_POWER_PER_DAY;}
}
