
package simulation;

import java.io.DataInput;

public class Simulation {

    protected static int DayInput(){
        System.out.println("Witaj w programie symulującym działanie portu morskiego.\n" +
                "Wprowadź liczbę dni, odpowiadającą długości trwania okresu mierzonego.\n" +
                "Dodatkowe informacje o przebiegu symulacji zostaną zawarte w pliku \"Logs\".");
        Scanner In = new Scanner(System.in);
        int x = In.nextInt();
    return x;}
    /*protected static void Inputcheck(int Input) {
        Scanner In = new Scanner(System.in);
        x=In.nextInt()
        if(x<=365) {
        return x;}
        else if (x>365){
        x=365;
        return x;}
        else {
            System.out.println("następnym razem wprowadź właściwą wartość.");}*/
    }
    public static void main(String[] args) {
        System.out.println(DayInput());

        }

    
}
