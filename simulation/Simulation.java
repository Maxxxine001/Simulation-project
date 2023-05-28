
package simulation;

import java.io.DataInput;
import java.util.Scanner;

public class Simulation {

    protected static int DayInput() {
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
    }
    public static void main(String[] args) {

    
    }
}

