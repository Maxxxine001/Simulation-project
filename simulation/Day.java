package simulation;
import java.util.Random;
import simulation.ships.Lifeboat;
import simulation.ships.Cargo;
import simulation.ships.Ship;
import simulation.ships.Civil;
import java.util.LinkedList;
import java.util.Iterator;

public class Day {

    //Konstruktor domyślny klasy, wywoływanie go
    public Day(){
        DayCount+=1;
        PassingDayActions();
        TotalIncome+=this.DailyIncome;
    }
    //Sekcja stałych symulacji (finalnych).

    private static final int LIFEBOATS_AMOUNT = 4;//Stała określająca ilość łodzi ratunkowych.
    private static final int MAX_CARGOS_IN_DOCKS = 8;//Maksymalna ilość statków Cargo, które mogą na raz przebywać w porcie.
    private static final int MAX_CARGOS_fLOW = 3;//Maksymalna ilość statków klasy Cargo, które mogą w danym dniu przepłynąć z i do portu.
    private static final int MAX_CIVILS_IN_DOCKS = 95;//Maksymalna ilość statków klasy Civil w porcie.
    private static final int MAX_DAILY_HAULING = 7;//Maksymalna ilość statków klasy Civil, która może jednego dnia wpłynąć do portu.
    private static final int MIN_DAILY_HAULINGS = 3;//Minimalna ilość statków klasy Civil, która może jednego dnia wpłynąć do portu.
    private static final int MAX_DAILY_CARGOS_DOCKING = 2;//Maksymalna ilość statków klasy Cargo, która może jednego dnia wpłynąć do portu.
    private static final int MIN_DAILY_CARGOS_DOCKING = 0;//Minimalna ilość statków klasy Cargo, która może jednego dnia wpłynąć do portu.
    private static final int MAX_CIVILS_AVAILABLE = 1660;//Maksymalna możliwa ilość wygenerowanych łodzi typu Civil (200+4*356).
    private static final int MAX_CARGOS_AVAILABLE = 466;//Maksymalna możliwa ilość wygenerowanych łodzi typu Cargo (100+356).

    //Sekcja stałych statycznych

    public static int DayAmount = Simulation.SimulationTime; //Zmienna zwracana z klasy głównej, deklarująca przez ile dni będzie trwała symulacja.
    private static int CargosAmount = 100+DayAmount;//Ilość statków klasy Cargo wygenerowanych dla całej symulacji.
    private static int CivilsAmount = 200+DayAmount*4;//Ilość statków klasy Civil, wygenerowanych dla całej symulacji.
    private static LinkedList<Ship> AllShips = GenerateShips();//Lista statków klas Cargo i Civil, które istnieją w symulacji, ale nie przebywają w porcie.
    private static LinkedList<Ship> DockedShips = new LinkedList<>();//Lista statków obecnie zadokowanych do portu.
    private static LinkedList<Lifeboat> Lifeboats = GenerateLifeboats(); //Osobna Lista zawierająca jednostki ratunkowe portu.

    //Sekcja zmiennych statycznych

    private static int DayCount = 0; //Odliczanie dni.
    protected static int TotalIncome=-LifeboatsRenting(); //Zmienna zliczająca zyski i straty ze wszystkich dni. Zaczynając od opłat za wynajęcie łodzi.
    private static int DockedCargosAmount = 0;//Całkowita liczba zadokowanych statków typu Cargo.
    private static int DockedCivilsAmount = 0;//Całkowita liczba zadokowanych statków typu Civil.
    private static int WaitingCargos = 0;//Liczba statków typu Cargo, oczekujących na wpłynięcie do portu.

    //Sekcja stałych specyficznych dla konkretnego dnia.

    private int DailyIncome=0;//Dzienny przychód portu.
    private int DailyCargoFlow=0;//Dzienny przepływ statków Cargo.
    private int DailyDockedCivils = 0;//Zadokowane tego dnia statki typu Civil.
    private int DailyDockedCargos = 0;//Zadokowane tego dnia statki typu Cargo.
    private int DailyLeftCivils = 0;//Statki typu civil, które opóściły port danego dnia.
    private int DailyLeftCargos = 0;//Statki typu Cargo, które opóściły port danego dnia.
    private boolean SailingPermission = true; //Port zamknięty lub otwarty dla ruchu morskiego.
    protected static String Log = GenerateFirstLog(); // Komunikat startowy, rozszerzany o następne dni w trakcie trwania symulacji.

    //sekcja Metod

    //Metoda zwracająca pierwszy komunikat zapisany w dzienniku.
    private static String GenerateFirstLog(){
        String OpeningMessage="Właściwości Symulacji:\n";
        OpeningMessage+="Czas trwania: "+DayAmount+" dni\n";
        OpeningMessage+="Wynajęte łodzie ratunkowe: "+LIFEBOATS_AMOUNT+"\n";
        OpeningMessage+="Całkowity koszt najmu łodzi ratunkowych: "+ -1*LifeboatsRenting()+"\n";
        OpeningMessage+="Maksymalna przepustowość portu statków towarowych w ciągu dnia: "+MAX_CARGOS_fLOW+"\n";
        OpeningMessage+="Ilość miejsc do wynajęcia dla statków cywilnych: "+MAX_CIVILS_IN_DOCKS+"\n";
        OpeningMessage+="Ilość miejsc w dokach, przeznaczonych dla statków towarowych: "+MAX_CARGOS_IN_DOCKS+"\n";
    return OpeningMessage;}
    //Metoda zwracajaca Liste statków typu Cargo i Civil biorących udział w symulacji:
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

    //Metoda zwracająca liste statków typu Lifeboat.
    private static LinkedList<Lifeboat> GenerateLifeboats (){
        LinkedList<Lifeboat>List = new LinkedList<Lifeboat>();
        Lifeboat[] Lifeboats = new Lifeboat[LIFEBOATS_AMOUNT];
        for (int i=0;i<LIFEBOATS_AMOUNT;i++){
            Lifeboats[i] = new Lifeboat(DayAmount);
            List.add(Lifeboats[i]);
        }
    return List;}

    //Metoda zliczająca koszt utrzymania łodzi,przez cały okres symulacji bez opłat za przeprowadzenie interwencji.
    private static int LifeboatsRenting(){
        int cost=0;
        for (Object RentedLifeboat : Lifeboats){
            Lifeboat lifeboat = (Lifeboat) RentedLifeboat;
            cost+=lifeboat.Rent();
        }
    return -cost;}

    //Metoda odpowiadająca za występowanie awarii statków Civil:
    private void CivilsAccidentsPossibility(){
        Random random = new Random();
        //Wygenerowanie prawdopodobieństwa  dla statków typu Civil o zależności logarytmicznej obecnej ilości statków tego typu do ich maksymalnej wartości.
        double probability = 0.1 * Math.log(CivilsAmount + 1) / Math.log(MAX_CIVILS_AVAILABLE);
        if(random.nextDouble()<probability){
            //W przypadku zajścia wypadku zachodzą następujące czynności:
            this.SailingPermission = false;//Wprowadzenie zakazu żeglugi.
            Iterator<Ship> iterator = AllShips.iterator();
            //Przeszukanie listy statków znajdujących się na morzu, w celu wyznaczenia statku cywilnego.
            while (iterator.hasNext()){
                Ship DamagedShip = iterator.next();
                if(DamagedShip instanceof Civil){
                    //Poniesienie kosztu w przypadku przeprowadzenia akcji ratunkowej na statku klasy Civil.
                    this.DailyIncome-=Lifeboat.InterveningCost((Civil)DamagedShip);
                    //Wykluczenie statku z dalszego ingerowania w model.
                    iterator.remove();
                    break;
                }
            }
        }
    }

    //Metoda odpowiadająca za występowanie awarii statków Cargo:
    private void CargosAccidentsPossibility(){
        Random random = new Random();
        //Wygenerowanie prawdopodobieństwa  dla statków typu Cargo o zależności logarytmicznej obecnej ilości statków tego typu do ich maksymalnej wartości.
        double probability = 0.015*Math.log(CargosAmount+1)/Math.log(MAX_CARGOS_AVAILABLE);//Logarytmiczna zależność pomiędzy ilością statków cargo, a częstotliwością awarii:
        if(random.nextDouble()<probability) {
            this.SailingPermission = false;//Wprowadzenie  zakazu żeglugi.
            Iterator<Ship> iterator = AllShips.iterator();
            //Przeszukanie listy statków znajdujących się na morzu, w celu wyznaczenia statku cywilnego.
            while (iterator.hasNext()) {
                Ship DamagedShip = iterator.next();
                if (DamagedShip instanceof Cargo) {
                    //Poniesienie kosztu w przypadku przeprowadzenia akcji ratunkowej na statku klasy Cargo.
                    this.DailyIncome-=Lifeboat.InterveningCost((Cargo)DamagedShip);
                    //Wykluczenie statku z dalszego ingerowania w model.
                    iterator.remove();
                    break;
                }
            }
        }
    }
    //Poniższa metoda odpowiada za opuszczenie statków z portu po określonym czasie pobytu.
    private void ShipsLeavingDocks(){
        //Iteracja przechodzi przez wszystkie elementy listy zadokowanych statków, zmniejszając każdemu pozostały czas wynajmowania miejsca.
        Iterator<Ship> iterator = DockedShips.iterator();
        //Warunek 1 przerywa pętle, w przypadku gdy ruch morski zostanie zamknięty z powodu wypadku.
        //Warunek 2 przerywa pętle, kiedy iterator skończy przeszukiwać listę DockedShips.
        while (this.SailingPermission && iterator.hasNext()) {
            Ship DockedShip = iterator.next();
            //W przypadku kiedy statek jest klasy Cargo:
            if (DockedShip instanceof Cargo) {
                Cargo DockedCargo = (Cargo) DockedShip;
                DockedCargo.ResidenceDays--;//Zmniejszenie czasu pobytu statku o 1 dzień.
                //Jeśli maksymalny dzienny przepływ statków Cargo nie został przekroczony i czas ich pobytu upłynął, zachodzą następujące zmiany:
                if (this.DailyCargoFlow < MAX_CARGOS_fLOW && DockedCargo.ResidenceDays <= 0) {
                    this.DailyCargoFlow++;//Rejestrowany zostaje ruch statku na rzecz ograniczenia przepływu.
                    DockedCargosAmount--;//Liczba statków typu Cargo znajdujących się w porcie zostaje zmniejszona o 1.
                    AllShips.add(DockedShip);//Statek jest dodawany do listy statków znajdujących się na morzu.
                    iterator.remove();//Statek jest usuwany z listy statków znajdujących się w dokach.
                    this.DailyLeftCargos++;//Rejestrowany zostaje statek wypływający z portu danego dnia.
                }
            }
            //W przypadku kiedy statek jest klasy Civil:
            else if (DockedShip instanceof Civil) {
                Civil DockedCivil = (Civil) DockedShip;
                DockedCivil.ResidenceDays--;//Zmniejszenie czasu pobytu statku o 1 dzień.
                if (DockedCivil.ResidenceDays <= 0) {//Jeśli danemu statkowi upłynie termin pobytu, zachodzą poniższe zmiany:
                    DockedCivilsAmount--;//Liczba statków typu Civil znajdujących się w porcie zostaje zmniejszona o 1.
                    AllShips.add(DockedShip);//Statek jest dodawany do listy statków znajdujących się na morzu.
                    iterator.remove();//Statek jest usuwany z listy statków znajdujących się w dokach.
                    this.DailyLeftCivils++;//Rejestrowany zostaje statek wypływający z portu danego dnia.
                }
            }
        }
    }
    //Poniższa metoda odpowiada za opuszczenie statków z portu po określonym czasie pobytu.
    private void CivilsDocking() {
        Random random = new Random();
        //Wygenerowana liczba statków klasy Civil przybijająca tego dnia do portu  z zakresu (MIN_DAILY_HAULINGS,MAX_DAILY_HAULINGS):
        int TodaysHaulingCivils = random.nextInt(MAX_DAILY_HAULING - MIN_DAILY_HAULINGS + 1) + MIN_DAILY_HAULINGS;
        //Warunek 1 przerywa pętle, w przypadku gdy ruch morski zostanie zamknięty z powodu wypadku.
        //Warunek 2 przerywa pętle, kiedy statków cywilnych jest zbyt dużo, żeby zmieścić się w porcie.
        //Warunek 3 przerywa pętle, kiedy skończą się nowo wygenerowane chcące wpłynąć do portu.
        while (this.SailingPermission && DockedCivilsAmount < MAX_CIVILS_IN_DOCKS  && TodaysHaulingCivils > 0) {
            Iterator<Ship> iterator = AllShips.iterator();
            while (iterator.hasNext()) {
                Ship SomeShip = iterator.next();
                //Kiedy znajdzie pierwszy element odpowiadający typowi Civil...
                if (SomeShip instanceof Civil) {
                    Civil HaulingCivil = (Civil) SomeShip;
                    //Dla znalezionego statku typu Civil z listy zachodzą następujące czynności:
                    DockedShips.add(SomeShip);//Dodanie statku do listy zadokowanych statków.
                    this.DailyIncome += HaulingCivil.Rent();//Doliczenie do dziennego przychodu kosztu najmu tego statku.
                    this.DailyDockedCivils++;
                    DockedCivilsAmount++;//Zarejestrowanie statku w spisie wszystkich statków typu Civil.
                    TodaysHaulingCivils--;
                    iterator.remove();//Usunięcie elementu z listy statków znajdujących się na morzu.
                    break;
                }
            }
        }
    }

    //Metoda odpowiadająca za wpływanie statków typu Cargo do portu:
    private void CargosDocking(){
        Random random = new Random();
        //Wygenerowana liczba statków klasy Civil przybijająca tego dnia do portu  z zakresu (MAX_DAILY_CARGOS_DOCKING,MIN_DAILY_CARGOS_DOCKING):
        int TodaysWaitingCargos = random.nextInt(MAX_DAILY_CARGOS_DOCKING - MIN_DAILY_CARGOS_DOCKING + 1) + MIN_DAILY_CARGOS_DOCKING;
        WaitingCargos+=TodaysWaitingCargos;
        //Warunek 1 przerywa pętle, w przypadku gdy ruch morski zostanie zamknięty z powodu wypadku.
        //Warunek 2 przerywa pętle, w przypadku osiągnięcia maksymalnej przepustowości portu danego dnia.
        //Warunek 3 przerywa pętle, w przypadku przekroczenia maksymalnej liczby statków typu Cargo mieszczących się w porcie.
        //Warunek 4 przerywa pętle, w przypadku braku czekających statków typu Cargo.
        while(this.SailingPermission && this.DailyCargoFlow < MAX_CARGOS_fLOW && DockedCargosAmount < MAX_CARGOS_IN_DOCKS && WaitingCargos > 0){
            //Poniższe czynności zachodzą, dopóki 1 z powyższych warunków nie zostanie spełniony.
            Iterator<Ship> iterator = AllShips.iterator();
            //Iteracja przechodzi przez wszystkie elementy listy statków znajdujących się na morzu.
            while (iterator.hasNext()){
                Ship SomeShip = iterator.next();
                //Przeszukując listę wszystkich statków trafia na pierwszy o typie Cargo.
                if (SomeShip instanceof Cargo) {
                    Cargo DockingCargo = (Cargo) SomeShip;
                    //Dla znalezionego statku typu Cargo z listy zachodzą następujące czynności:
                    this.DailyIncome += DockingCargo.Rent();//Doliczenie do dziennego przychodu kosztu najmu tego statku.
                    this.DailyCargoFlow++;//Uwzględnienie statku w ograniczeniu przepływu portu.
                    this.DailyDockedCargos++;//Zarejestrowanie statku w spisie statków typu Cargo, które tego dnia przypłynęły.
                    DockedCargosAmount++;//Zarejestrowanie statku w spisie wszystkich statków typu Cargo.
                    WaitingCargos--;//Zmniejszenie liczby czekających statków Cargo na wpłynięcie o dany statek.
                    DockedShips.add(SomeShip);//Dodanie statku do listy zadokowanych statków.
                    iterator.remove();//Usunięcie elementu z listy statków znajdujących się na morzu.
                    break;//Następnie pętla jest przerywana.
                }
            }
        }
    }
    //Metoda zapisująca raport z danego dnia.
    private void SavingDayLogs(){
        Log += "\nDay " + DayCount + ".\n";
        if(SailingPermission) {
            Log += "Dzienny przychód: "+this.DailyIncome+"\n";
            Log += "Zwodowane statki cywilne: "+this.DailyDockedCivils+"\n";
            Log += "Statki towarowe, które przybyły do portu: "+this.DailyDockedCargos+"\n";
            Log += "Ilość statków cywilnych przebywających w porcie: "+DockedCivilsAmount+"\n";
            Log += "Ilość statków towarowych przebywających w dokach: "+DockedCargosAmount+"\n";
            Log += "Statki cywilne które opóściły port: "+this.DailyLeftCivils+"\n";
            Log += "Statki towarowe które wypłynęły w morze: "+this.DailyLeftCargos+"\n";
            Log += "Statki towarowe oczekujące na wejście do portu: "+WaitingCargos+"\n";
        }
        else{
            Log+="Ten dzień nie był szczęśliwy dla marynarzy...\n" +
                    "W związku z zajściem poważnego wypadku na morzu, port został zamknięty, a podjęcie akcji ratunkowej przez służby kosztowało port: "+this.DailyIncome+"\n";
        }
    }
    //Metoda sumująca wszystkie inne metody, uwzględniane przy symulowaniu przebiegu dnia.
    private void PassingDayActions(){
        CargosAccidentsPossibility();
        CivilsAccidentsPossibility();
        ShipsLeavingDocks();
        CivilsDocking();
        CargosDocking();
        SavingDayLogs();
    }
}
