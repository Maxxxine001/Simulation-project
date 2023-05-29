package simulation;
import java.io.PrintWriter;
import java.io.DataInput;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Simulation {
    public static int DayInput() {
        Scanner scanner = new Scanner(System.in);
        int liczba;
        System.out.println("Witaj w programie symulującym działanie portu morskiego.\n" +
                "Wprowadź liczbę dni, odpowiadającą długości trwania okresu mierzonego  z przedziału 1 - 365.\n" +
                "Dodatkowe informacje o przebiegu symulacji zostaną zawarte w pliku \"Logs\".");
        while (true) {
            System.out.print("\n---> ");
            try {
                liczba = scanner.nextInt();
                if (liczba >= 1 && liczba <= 365) {

                    break;
                } else if (liczba >= 365){
                    System.out.println("Wprowadzona liczba zostanie zamieniona na maksymalną dostępną wartość");
                    liczba = 365;
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzona wartość musi być liczbą całkowitą, w zakresie od 1 do 365.");
                scanner.nextLine();
            }
        }
        return liczba;
    }
    private static void SaveToTxt(String data){
        try {
            PrintWriter out = new PrintWriter("Logs.txt");
            out.println(data);
            out.close();
        } catch(Exception exception) {
            System.out.println("Zapis do pliku nie powdiódł się.");
        }
    }
    private static void RunSimulation(int Time){
        Day.DayAmount = Time;
        Day[] simulation = new Day[Time];
        for (int i=0;i<Time;i++){
        simulation[i] = new Day();
        }
    }
    private static void FinalCommunicate(){
        System.out.println("W ciągu trwania symulacji, port wygenerował łączny przychód "+Day.TotalIncome);
    }
    public static void main(String[] args) {
        int SimulationTime = DayInput();
        RunSimulation(SimulationTime);
        SaveToTxt(Day.Log);
        FinalCommunicate();
    }
}

