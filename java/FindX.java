import java.util.*;

public class FindX {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int numbers[] = new int[size];
        // for input
        for( int i= 0; i<size ; i++){
           numbers[i] = sc.nextInt();
        }
        int x=sc.nextInt();
        // for output
        for( int i= 0; i<size ; i++){
           if(numbers[i] == x){
            System.out.println("x were found on index :"+ i);
           } else{
            System.out.println(" invalid value");
           }
        }
        sc.close();
    }
}