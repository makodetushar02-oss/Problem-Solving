import java.util.*;

public class TwoDarray {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter number of row");
        int row = sc.nextInt();
        System.out.println("enter number of columb");
        int columb = sc.nextInt();
        int numbers[][] = new int[row][columb];
        // for input
        for( int i= 0; i<row ; i++){
            for( int j= 0 ; j<columb ; j++){
           numbers[i][j] = sc.nextInt();
          }
        }
        // for output
        for( int i= 0; i<row ; i++){
           for( int j= 0; j<columb ; j++){
            System.out.print(numbers[i][j] + " ");
           }
           System.out.println();
        }
        sc.close();
    }
}