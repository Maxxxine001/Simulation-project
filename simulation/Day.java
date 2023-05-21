package simulation;

import simulation.ships.Lifeboat;
import simulation.ships.Cargo;
import simulation.ships.Ship;
import simulation.ships.Civil;
import java.util.LinkedList;

public class Day {
    private static final int DayAmount = 10; //do zmiany
    private static final int LifeboatsAmount = 2;
    private static final int CargosAmount = 450;
    private static final int MAXCARGOSINDOCKS = 8;
    private static final int CivilsAmount = 200+DayAmount*3;
    private static final int MAXCIVILSINDOCKS = 95;
    private static int DayCount = 1;
    protected static LinkedList AllShips = GenerateShips();
    protected static LinkedList DockedShips = new LinkedList<Ship>();
    protected int DailyIncome=0;
    private LinkedList GenerateShips (){ //funkcja odpowiadająca za utworzenie listy wszystkich statków znajdujących się na akwenie
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
    Day(){

    }

}
