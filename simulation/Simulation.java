
package simulation;

import java.io.DataInput;
import java.util.Scanner;

public class Simulation {

    protected static void DayInput() {
        System.out.println("Witaj w programie symulującym działanie portu morskiego.\n" +
                "Wprowadź liczbę dni, odpowiadającą długości trwania okresu mierzonego.\n" +
                "Dodatkowe informacje o przebiegu symulacji zostaną zawarte w pliku \"Logs\".");
        Scanner In = new Scanner(System.in);
        int x = In.nextInt();
    }
    public static void main(String[] args) {


    }
}

