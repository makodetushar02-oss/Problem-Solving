import java.util.*;
public class PrimeNumber{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean isPrime = false;
        for (int i = 2; i < n; i++) {
            if( n%i == 0) {
                isPrime = false;
                break;
            }
            else isPrime = true;
        }
        if(isPrime) System.out.println("Number is Prime");
        else System.out.println("Number is Not prime");
        sc.close();
    }
}