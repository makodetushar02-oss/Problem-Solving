import java.util.*;
public class PalindromNumber{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = n;
        int sum = 0;
        while(n != 0){
             int rem = n %10 ;
            sum = sum*10 + rem;
            n = n/ 10;
        }
        System.out.println(sum == n ? "Number Is Palindrom" : "Number Is Not Palindrom");
    }
}