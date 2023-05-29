package simulation;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Simulation {

    //Sekcja metod:

    //Metoda pozyskująca od użytkownika liczbę dni, potrzebną do rozpoczęcia symulacji.
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
                    System.out.println("Wprowadzona liczba zostanie zamieniona na maksymalną dostępną wartość (365).");
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
    //Metoda odpowiedzialna za zapis dzienników w postaci pojedyńczego stringu, wygenerowanych przez klasę Day do pliku tekstowego.
    private static void SaveToTxt(String data){
        try {
            PrintWriter out = new PrintWriter("Logs.txt");
            out.println(data);
            out.close();
        } catch(Exception exception) {
            System.out.println("Zapis do pliku nie powdiódł się.");
        }
    }
    //Metoda generująca tablicę obiektów klasy Day, wykorzystująca domyślny konstruktor w celu symulowania przebiegu kolejnych dni.
    private static void RunSimulation(){
        Day[] simulation = new Day[SimulationTime];
        for (int i=0;i<SimulationTime;i++){
        simulation[i] = new Day();
        }
    }
    //Metoda zwracająca użytkownikowi Ostateczny komunikat, wraz z wynikiem końcowym wartości odpowiadającej finalnemu przychodowi.
    private static void FinalCommunicate(){
        System.out.println("W ciągu trwania symulacji, port wygenerował łączny przychód "+Day.TotalIncome);
    }

    public static int SimulationTime;//Jedyna zmienna klasy głównej.
    //Potrzebna przede wszystkim w celu przekazania podanej przez użytkownika informacji odnoszącej się do czasu trwania symulacji do klasy Day.

    //Główna metoda, ciało programu.
    public static void main(String[] args) {
        SimulationTime = DayInput();
        RunSimulation();
        SaveToTxt(Day.Log);
        FinalCommunicate();
    }
}

