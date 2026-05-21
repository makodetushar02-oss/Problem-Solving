import java.util.*;

public class Problem21 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in) ; 
        System.out.println("enter number of column :");
        int column = sc.nextInt();
        System.out.println("enter number of row :");
        int row = sc.nextInt();
        int[][] matrix = new int[column][row];
        for(int i = 0 ; i < column; i++){
            for(int j = 0 ; j< row; j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        int[][] transpose = new int[column][row];
         for(int i = 0 ; i < column; i++){
            for(int j = 0 ; j< row; j++){
                transpose[j][i] =matrix[i][j] ;
            }
        }
         for(int i = 0 ; i < column; i++){
            for(int j = 0 ; j< row; j++){
                System.out.print(transpose[i][j] + " ");
            }
            System.out.println();
        }
        sc.close();
    }
    
}
