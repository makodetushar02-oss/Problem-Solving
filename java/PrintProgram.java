import java.util.*;
import java.util.Arrays;
public class PrintProgram {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int numbers[] = new int[size];
        // for input
        for( int i= 0; i<size ; i++){
           numbers[i] = sc.nextInt();
        }
        // for output
        for( int i= 0; i<size ; i++){
           System.out.println( numbers[i] );
        }
        Arrays.sort(numbers);
        System.out.println("Sorted Array :");
        for( int i = 0 ; i<numbers.length; i++){
            System.out.println(numbers[i] + " ");
        }

        sc.close();
    } 
}