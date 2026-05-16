import java.util.Scanner;
import java.util.Arrays;
public class ReverseArray {
    public static void main(String[] args){
        // Implementation for reversing an array
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of array:");
        int n = sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter elements of array:");
        for(int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        for(int j = 0; j < n/2; j++){
            int temp = arr[j];
            arr[j] = arr[n-1-j];
            arr[n-1-j] = temp;
        }
        System.out.println("Reverse Array : " + Arrays.toString(arr));
        sc.close();
    }

}
