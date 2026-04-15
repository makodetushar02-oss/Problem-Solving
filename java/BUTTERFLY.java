import java.util.*;
public class BUTTERFLY {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
       // first half 
       for ( int i = 1; i<= n ; i++){
        // first part
        for( int j = 1 ; j<= i ; j++){
            System.out.print("*");
        }
        // space
        int k= 2*(n-i) ;
        for( int j = 1 ; j<= k ; j++){
            System.out.print(" ");
        }
        //second part
        for( int j = 1 ; j<= i ; j++){
            System.out.print("*");
        }
        System.out.println();
       }
       // second half 
       for ( int i = n; i>=1 ; i--){
        // first part
        for( int j = 1 ; j<= i ; j++){
            System.out.print("*");
        }
        // space
        int k= 2*(n-i) ;
        for( int j = 1 ; j<= k ; j++){
            System.out.print(" ");
        }
        //second part
        for( int j = 1 ; j<= i ; j++){
            System.out.print("*");
        }
        System.out.println();
       }
        sc.close();
    }
}