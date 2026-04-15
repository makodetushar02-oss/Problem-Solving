import java.util.*;
public class InversPyramid {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
       // int m = sc.nextInt();
        for (int i = n ; i >= 1 ; i--)  {
             for( int j = 1 ; j <= i ; j++) {
              // if ( i == 1 || j == 1 || i == n || j == m ) {
                System.out.print("*");
             //} else {
                System.out.print(" ");
           //  }
            }
            System.out.println();
        }
        sc.close();
    }
}