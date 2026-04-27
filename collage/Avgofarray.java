 import java.util.*;

 public class Avgofarray {
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
         int n = sc.nextInt();
         int[] arr = new int[n];
         for(int i=0; i<n; i++){
             arr[i] = sc.nextInt();
        }
         int sum = 0;
         for(int i=0; i<n; i++){
             sum += arr[i];
         }
         System.out.println(" the AVG. of Array is : " + sum/arr.length);
         System.out.println("Sum of array: " + sum);
         sc.close();
    }
}