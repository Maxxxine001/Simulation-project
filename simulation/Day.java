package simulation;
import java.util.Random;
import simulation.ships.Lifeboat;
import simulation.ships.Cargo;
import simulation.ships.Ship;
import simulation.ships.Civil;
import java.util.LinkedList;
import java.util.Iterator;

public class Day {

    //Sekcja stałych symulacji (finalnych).

    private static final int LIFEBOATS_AMOUNT = 2;//Stała określająca ilość łodzi ratunkowych.
    private static final int MAX_CARGOS_IN_DOCKS = 8;//Maksymalna ilość statków Cargo, które mogą na raz przebywać w porcie.
    private static final int MAX_CARGOS_fLOW = 3;//Maksymalna ilość statków klasy Cargo, które mogą w danym dniu przepłynąć z i do portu.
    private static final int MAX_CIVILS_IN_DOCKS = 95;//Maksymalna ilość statków klasy Civil w porcie.
    private static final int MAX_DAILY_HAULING = 7;//Maksymalna ilość statków klasy Civil, które mogą jednego dnia wpłynąć do portu.
    private static final int MIN_DAILY_HAULINGS = 3;//Minimalna ilość statków klasy Civil, które mogą jednego dnia wpłynąć do portu.

    //Sekcja stałych statycznych

    public static int DayAmount; //Zmienna zwracana z klasy głównej, deklarująca przez ile dni będzie trwała symulacja. Potrzebna do wygenerowania ograniczonej liczby statków.
    private static int CargosAmount = 100+DayAmount;//Ilość statków klasy Cargo wygenerowanych dla całej symulacji.
    private static int CivilsAmount = 200+DayAmount*3;//Ilość statków klasy Civil, wygenerowanych dla całej symulacji.
    protected static LinkedList<Ship> AllShips = GenerateShips();//Lista statków klas Cargo i Civil, które istnieją w symulacji, ale nie przebywają w porcie.
    protected static LinkedList<Ship> DockedShips = new LinkedList<>();//Lista statków obecnie zadokowanych do portu.
    protected static LinkedList<Lifeboat> Lifeboats = GenerateLifeboats(); //Osobna Lista zawierająca jednostki ratunkowe portu.

    //Sekcja zmiennych statycznych

    private static int DayCount = 1; //Odliczanie dni.
    protected static int TotalIncome=-LifeboatsRenting(); //Zmienna zliczająca zyski i straty ze wszystkich dni. Zaczynając od opłat za wynajęcie łodzi.
    protected static int DockedCargosAmount = 0;//Całkowita liczba zadokowanych statków typu Cargo.
    protected static int DockedCivilsAmount = 0;//Całkowita liczba zadokowanych statków typu Civil.
    protected static int WaitingCargos = 0;//Liczba statków typu Cargo, oczekujących na wpłynięcie do portu.

    //Sekcja stałych specyficznych dla konkretnego dnia.

    protected int DailyIncome=0;//Dzienny przychód portu.
    private int DailyCargoFlow=0;//Dzienny przepływ statków Cargo.
    protected int DailyDockedCivils = 0;//Zadokowane tego dnia statki typu Civil.
    protected int DailyDockedCargos = 0;//Zadokowane tego dnia statki typu Cargo.
    protected boolean SailingPermission = true; //Port zamknięty lub otwarty dla ruchu morskiego.
    public static String Log = GenerateFirstLog(); // Komunikat startowy, rozszerzany o następne dni w trakcie trwania symulacji.

    //sekcja funkcji

    //Funkcja generująca pierwszy komunikat zapisany w dzienniku.
    private static String GenerateFirstLog(){
        String OpeningMessage="Simulation Specifications:\n"
                +"Time duration: "+DayAmount+"days\n"
                +"Lifeboats rented: "+LIFEBOATS_AMOUNT+"\n"
                +"Total cost of rented lifeboats: "+ LifeboatsRenting()+"\n"
                +"Maximal flow of cargo ships: "+MAX_CARGOS_fLOW+"\n";
    return OpeningMessage;}
    //Funkcja zwracajaca Liste statków typu Cargo i Civil biorących udział w symulacji:
    private static LinkedList<Ship> GenerateShips () {
    LinkedList List = new LinkedList<Ship>();//Wygenerowanie listy.
        //dodawanie łodzi towarowych.
        Cargo[] Cargos = new Cargo[CargosAmount];
        for(int i=0;i<CargosAmount;i++){
        Cargos[i] = new Cargo();
        List.add(Cargos[i]);
        }
        //dodawanie łodzi cywilnych.
        Civil[] Civils = new Civil[CivilsAmount];
        for(int i=0;i<CivilsAmount;i++){
        Civils[i]= new Civil();
        List.add(Civils[i]);
        }
    return List;}

    //Funkcja zwracająca liste statków klasy Lifeboat.
    private static LinkedList<Lifeboat> GenerateLifeboats (){
        LinkedList<Lifeboat>List = new LinkedList<Lifeboat>();
        Lifeboat[] Lifeboats = new Lifeboat[LIFEBOATS_AMOUNT];
        for (int i=0;i<LIFEBOATS_AMOUNT;i++){
            Lifeboats[i] = new Lifeboat(DayAmount);
            List.add(Lifeboats[i]);
        }
    return List;}

    //Funkcja zliczająca koszt utrzymania łodzi,przez cały okres symulacji bez opłat za przeprowadzenie interwencji.
    private static int LifeboatsRenting(){
        int cost=0;
        for (Object RentedLifeboat : Lifeboats){
            Lifeboat lifeboat = (Lifeboat) RentedLifeboat;
            cost+=lifeboat.Rent();
        }
    return cost;}

    //Funkcja odpowiadająca za występowanie awarii statków Civil:
    private void CivilsAccidentsPossibility(){
        Random random = new Random();
        double probability = 0.1 * Math.log(CivilsAmount + 1) / Math.log(1296);
        if(random.nextDouble()<probability){
            SailingPermission = false;
            AllShips.remove();
        }
    }

    //Funkcja odpowiadająca za występowanie awarii statków Cargo:
    private void CargosAccidentsPossibility(){
        Random random = new Random();
        double probability = 0.015*Math.log(CargosAmount+1)/Math.log(466);//Logarytmiczna zależność pomiędzy ilością statków cargo, a częstotliwością awarii:
        if(random.nextDouble()<probability) {
            this.SailingPermission = false;
            for(Object DamagedCargo : AllShips){
                if(DamagedCargo instanceof Cargo){
                    AllShips.remove(DamagedCargo);
                    DailyIncome-=Lifeboat.InterveningCost((Cargo) DamagedCargo);
                break;}
            }
        }
    }
    //Funkcja odpowiadająca za wpływanie statków typu Cargo, wraz ze wszystkimi ograniczeniami tej mechaniki.
    protected void CargosDocking(){
        Random random = new Random();
        int TodaysWaitingCargos = random.nextInt(3) ; //Ta zmienna definiuje ile statków cargo przypłynie w danym dniu (od 0 do 2).
        WaitingCargos+=TodaysWaitingCargos;
        //warunek1 odnosi się do pozwolenia na płynięcie wydane przez służby, warunek 2 odnosi się do maksymalnej przepustowości portu, 3 warunek odnosi się do maksymalnej pojemności portu.
        while(this.SailingPermission && this.DailyCargoFlow<MAX_CARGOS_fLOW && DockedCargosAmount<MAX_CARGOS_IN_DOCKS){
            //Iteracja przechodzi przez wszystkie elementy listy statków znajdujących się na morzu.
            Iterator<Ship> iterator = AllShips.iterator();
            while (iterator.hasNext()){
                Ship SomeShip = iterator.next();
                //Kiedy znajdzie pierwszy element odpowiadający typowi Cargo...
                if (SomeShip instanceof Cargo) {
                    Cargo DockingCargo = (Cargo) SomeShip;
                    //Dla znalezionego statku typu Cargo z listy zachodzą następujące czynności:
                    DockedShips.add(SomeShip);//Dodanie statku do listy zadokowanych statków.
                    this.DailyIncome += DockingCargo.Rent();//Doliczenie do dziennego przychodu kosztu najmu tego statku.
                    this.DailyCargoFlow += 1;//Uwzględnienie statku w ograniczeniu przepływu portu.
                    this.DailyDockedCargos +=1;//Zarejestrowanie statku w spisie statków typu Cargo, które tego dnia przypłynęły.
                    DockedCargosAmount += 1;//Zarejestrowanie statku w spisie wszystkich statków typu Cargo.
                    WaitingCargos -= 1;//Zmniejszenie liczby czekających statków Cargo na wpłynięcie o dany statek.
                    iterator.remove();//Usunięcie elementu z listy statków znajdujących się na morzu.
                    break;
                }
            }
        }
    }
    //Poniższa funkcja odpowiada za opuszczenie statków z portu po określonym czasie pobytu.
    protected void ShipsLeavingDocks(){
        //Iteracja przechodzi przez wszystkie elementy listy zadokowanych statków, zmniejszając każdemu pozostały czas wynajmowania miejsca.
        Iterator<Ship> iterator = DockedShips.iterator();
        //1 warunek związany z pozwoleniem na żeglugę, 2 warunek związany z iterowaniem przez listę zadokowanych statków.
        while (this.SailingPermission && iterator.hasNext()) {
            Ship DockedShip = iterator.next();
            //W przypadku kiedy statek jest klasy Cargo:
            if (DockedShip instanceof Cargo) {
                while (this.DailyCargoFlow < MAX_CARGOS_fLOW) {//Dodatkowe Ograniczenie spowodowane maksymalnym przepływem statków cargo.
                    Cargo DockedCargo = (Cargo) DockedShip;
                    DockedCargo.ResidenceDays -= 1;//Zmniejszenie czasu pobytu statku o 1 dzień
                    if (DockedCargo.ResidenceDays <= 0) {//Jeśli danemu statkowi upłynie termin pobytu, zachodzą poniższe zmiany:
                        AllShips.add(DockedShip);//Statek jest dodawany do listy statków znajdujących się na morzu.
                        this.DailyCargoFlow += 1;//Rejestrowany zostaje ruch statku na rzecz ograniczenia przepływu.
                        DockedCargosAmount -= 1;//Liczba statków typu Cargo znajdujących się w porcie zostaje zmniejszona o 1.
                        iterator.remove();//Statek jest usuwany z listy statków znajdujących się w dokach.
                    }
                }
            }
            //W przypadku kiedy statek jest klasy Civil:
            else if (DockedShip instanceof Civil) {
                Civil DockedCivil = (Civil) DockedShip;
                DockedCivil.ResidenceDays -= 1;//Zmniejszenie czasu pobytu statku o 1 dzień.
                if (DockedCivil.ResidenceDays <= 0) {//Jeśli danemu statkowi upłynie termin pobytu, zachodzą poniższe zmiany:
                    AllShips.add(DockedShip);//Statek jest dodawany do listy statków znajdujących się na morzu.
                    DockedCivilsAmount -= 1;//Liczba statków typu Civil znajdujących się w porcie zostaje zmniejszona o 1.
                    iterator.remove();//Statek jest usuwany z listy statków znajdujących się w dokach.
                }
            }
        }
    }

    protected void CivilsDocking() {
        Random random = new Random();
        int TodaysHaulingCivils = random.nextInt(MAX_DAILY_HAULING - MIN_DAILY_HAULINGS) + MIN_DAILY_HAULINGS;//Wygenerowana liczba statków klasy Civil przybijająca tego dnia do portu (MIN_DAILY_HAULINGS,MAX_DAILY_HAULINGS)
        //Warunek 1 odnosi się do pozwolenia na płynięcie wydane przez służby, warunek 2 odnosi się do maksymalnej ilości statków klasy Civil mogących przebywać w porcie, 3 warunek odlicza liczbę nowych statków przypadajacyh na ten konkretny dzień.
        while (this.SailingPermission && DockedCivilsAmount <= MAX_CIVILS_IN_DOCKS  && TodaysHaulingCivils > 0) {
            Iterator<Ship> iterator = AllShips.iterator();
            while (iterator.hasNext()) {
                Ship SomeShip = iterator.next();
                //Kiedy znajdzie pierwszy element odpowiadający typowi Civil...
                if (SomeShip instanceof Civil) {
                    Civil HaulingCivil = (Civil) SomeShip;
                    //Dla znalezionego statku typu Civil z listy zachodzą następujące czynności:
                    DockedShips.add(SomeShip);//Dodanie statku do listy zadokowanych statków.
                    this.DailyIncome += HaulingCivil.Rent();//Doliczenie do dziennego przychodu kosztu najmu tego statku.
                    DockedCivilsAmount += 1;//Zarejestrowanie statku w spisie wszystkich statków typu Civil.
                    TodaysHaulingCivils -= 1;
                    iterator.remove();//Usunięcie elementu z listy statków znajdujących się na morzu.
                    break;
                }
            }
        }
    }
    protected void SavingDayLogs(){
        Log += "\nDay " + DayCount + ".\n";
        if(SailingPermission) {
            Log += "Daily Income: "+this.DailyIncome+"\n";
            Log += "Hauled civil ships: "+DailyDockedCivils+"\n";
            Log += "Docked cargo ships: "+DailyDockedCargos+"\n";
            Log += "Total amount of docked ships: "+DockedShips.size()+"\n";
            Log += "Waiting for the next day cargos: "+WaitingCargos+"\n";
        }
        else{
            Log+="This day was unfortunate for the sailors.\n" +
                    "The accident caused closing the sea traffic and generated cost total of: "+"\n";
        }
    }
    protected void PassingDayActions(){
        CargosAccidentsPossibility();
        CivilsAccidentsPossibility();
        ShipsLeavingDocks();
        CivilsDocking();
        CargosDocking();
        SavingDayLogs();
    }
    public Day(){
        DayCount+=1;
        PassingDayActions();
        TotalIncome+=this.DailyIncome;
    }
}
