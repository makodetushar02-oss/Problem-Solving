import java.util.*;

public class Largestnumberinarr{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array : ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0 ; i < n ; i++){
            arr[i] = sc.nextInt();
        }
        int large = arr[0];
        for( int i = 1 ; i < n ; i++){
            if(arr[i] > large){
                large = arr[i];
            }
        }
        System.out.println(" the largest number is :" + large);
        sc.close();
    }
}