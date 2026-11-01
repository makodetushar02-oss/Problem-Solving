import java.util.*;
public class FibonacciSeries {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(fibonacci(n));
        sc.close();
    }
    public static int  fibonacci(int n) {
        // base case
        if (n == 1 ||  n == 2) return n-1;
        //        fibonacci(n-1);
        // recurive call
        return fibonacci(n-1) + fibonacci(n-2);
    }
}