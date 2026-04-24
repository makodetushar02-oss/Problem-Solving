import java.util.*;

public class sumof2Darray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of rows : ");
        int n = sc.nextInt();
        System.out.println("Enter the elements of the column : ");
        int m = sc.nextInt();
        int[][] arr = new int[n][m];
        // Input elements in 2D array
        //for row 
        for(int i=0; i<n; i++){
            //for colmn
            for(int j=0 ; j < m ; j++){
                arr[i][j] =sc.nextInt();
            }
        }
        int sum = 0;
        //for sum of 2D array
        for(int i=0; i<n; i++){
            for( int j =0 ; j< m ; j++){
                sum += arr[i][j];
            }
        }
        System.out.println("Sum of  2Darray : " + sum);
        sc.close();
    }
}