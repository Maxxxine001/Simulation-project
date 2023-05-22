package simulation;
import java.util.Random;
import simulation.ships.Lifeboat;
import simulation.ships.Cargo;
import simulation.ships.Ship;
import simulation.ships.Civil;
import java.util.LinkedList;

public class Day {
    private static final int DayAmount = 10; //do zmiany
    private static final int LifeboatsAmount = 2;
    private static final int CargosAmount = 100+DayAmount;
    private static final int MAXCARGOSINDOCKS = 8;
    private static final int CivilsAmount = 200+DayAmount*3;
    private static final int MAXCIVILSINDOCKS = 95;
    private static final int MAXCARGOSFLOW = 3;
    //wszystko powyżej to stałe symulacji

    //wszystko poniżej to dane specyficzne dla konkretnego dnia
    private static int DayCount = 1;
    private static boolean SailingPermission = true;
    protected static LinkedList AllShips = GenerateShips();
    protected static LinkedList DockedShips = new LinkedList<Ship>();
    private static int PreviousWaitingCargos=0;
    protected int DailyIncome=0; //dane dla logów

    //sekcja funkcji poniżej
    private static LinkedList GenerateShips (){ //funkcja odpowiadająca za utworzenie listy wszystkich statków znajdujących się na akwenie
        int i;
    LinkedList List = new LinkedList<Ship>();
        //dodawanie łodzi ratunkowych
        Lifeboat[] Lifeboats = new Lifeboat[LifeboatsAmount];
        for(i=0;i<LifeboatsAmount;i++){
        Lifeboats[i] = new Lifeboat();
        List.add(Lifeboats[i]);
    }
        //dodawanie łodzi towarowych
        Cargo[] Cargos = new Cargo[CargosAmount];
        for(i=0;i<CargosAmount;i++){
        Cargos[i] = new Cargo();
        List.add(Cargos[i]);
    }
        //dodawanie łodzi cywilnych
        Civil[] Civils = new Civil[CivilsAmount];
        for(i=0;i<CivilsAmount;i++){
        Civils[i]= new Civil();
        List.add(Civils[i]);
        }
    return List;}

    protected static void CivilsAccidentsPossibility(){
        //logarytmiczna zależność pomiędzy ilością statków civil, a częstotliwością awarii:
        Random random = new Random();
        double probability = 0.1*Math.log(CivilsAmount+1)/Math.log(1296);
        if(random.nextDouble()<probability){
            SailingPermission=false;
            AllShips.remove();}
    }
    protected static void CargosAccidentsPossibility(){
        //logarytmiczna zależność pomiędzy ilością statków cargo, a częstotliwością awarii:
        Random random = new Random();
        double probability = 0.015*Math.log(CargosAmount+1)/Math.log(466);
        if(random.nextDouble()<probability) {
            SailingPermission = false;
            //poniżej część odpowiadająca za usunięcie statku (zatopiony lub odcholowany)
            for(Object DamagedCargo : AllShips){
                if(DamagedCargo instanceof Cargo){
                    AllShips.remove(DamagedCargo);
                }
            }
        }
    }
    protected static void CargosDocking(){
        Random random = new Random();
        int TodaysWaitingCargos = random.nextInt(3) ; //Ta zmienna definiuje ile statków cargo przypłynie w danym dniu (od 0 do 2)
        int Queue = TodaysWaitingCargos+PreviousWaitingCargos; //!!na koniec trzeba ustalić na nowo PWC w przypadku sailing permission
            if (SailingPermission=true) {
                //przerwa na kod odpowiadający za maksymalny przepływ statków
                for (Object DockingCargo : AllShips) {//ta część odpowiada za znalezienie elementru cargo z listy wszystkich statków i przeniesienie go do listy zdokowanych statków
                    if (DockingCargo instanceof Cargo) {
                        DockedShips.add(DockingCargo);
                        DailyIncome+=DockingCargo.Rent();
                        AllShips.remove(DockingCargo);
                        break;}
                }
            }
            else{ //(SailingPermission=False)
                PreviousWaitingCargos=Queue;
            }
    }
    protected static void ShipsLeavingDocks(){

    }
    protected static void CivilsDocking(){
    Random random = new Random();
    int TodaysHaulingCivils =
    }
    protected static void SavingLogs(){

    }
    protected static void PassingDayActions(){
        int TodaysCargoFlow = 0;
        SailingPermission=true;
        CargosAccidentsPossibility();
        CivilsAccidentsPossibility();
        ShipsLeavingDocks();
        CivilsDocking();
        CargosDocking();
        SavingLogs();
        DayCount+=1;
    }
}
