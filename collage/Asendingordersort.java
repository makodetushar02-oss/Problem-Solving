import java.util.Arrays;
import java.util.Scanner;

public class Asendingordersort{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        } 
        Arrays.sort(arr);
         System.out.print(Arrays.toString(arr));
        int i = 0;
        int j = n-1;
        while (i <= j) {
            if(arr[i] < arr[j]){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++ ;
                j-- ;
            }
        }
         System.out.print(Arrays.toString(arr));
        Arrays.sort(arr,Collections.reverseOrder());
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k] + " ");
        }
        System.out.print(Arrays.toString(arr));
        sc.close();
    }
}