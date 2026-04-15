import java.util.Scanner;

public class LeapYear{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Y = sc.nextInt();

        if( Y % 100 == 0){
            System.out.println("not a leap year");
        } 
        else if(Y % 4 == 0){
            System.out.println("leap year");
        }
        else{
            System.out.println("not a leap year");
        }
        sc.close();
    }
}