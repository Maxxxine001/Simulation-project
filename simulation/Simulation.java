package simulation;
import java.io.PrintWriter;
import java.io.DataInput;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Simulation {

    /*protected static int DayInput() {
        System.out.println("Witaj w programie symulującym działanie portu morskiego.\n" +
                "Wprowadź liczbę dni, odpowiadającą długości trwania okresu mierzonego  z przedziału 1 - 365.\n" +
                "Dodatkowe informacje o przebiegu symulacji zostaną zawarte w pliku \"Logs\".");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();

        if(x < 1 || x > 365){
            System.out.println("Podałeś liczbę spoza zakresu, wprowadź dane ponownie : \n");
            Scanner input2 = new Scanner(System.in);
            x = input2.nextInt();

            if(x < 1 || x > 365){
                DayInput();
            }
        }
        return x;
    }*/

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
                } else if(liczba => 365){
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
    public static void SaveToTxt(String data){
        try {
            PrintWriter out = new PrintWriter("Logs.txt");
        } catch(Exception exception) {
            System.out.println("Zapis do pliku nie powdiódł się.");
        }
    }
    public static void main(String[] args) {
    int DayAmount = DayInput();
    System.out.println(DayAmount);
    SaveToTxt(Day.Log);
    }
}

