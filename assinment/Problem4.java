import java.util.*;
public class Problem4 {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("enter the size of Array : ");
    int size = sc.nextInt();
    int arr[] = new int[size];

     for(int i = 0 ; i < size ; i++){
           arr[i] = sc.nextInt();
        }
         int largest = Integer.MIN_VALUE;
         int secondlargest = Integer.MIN_VALUE; 

        for(int j = 0; j < arr.length; j++){
            if(largest < secondlargest){
                secondlargest = largest;
                largest = arr[j];   
            }
            else if(arr[j]> secondlargest && arr[j] !=largest){
                secondlargest = arr[j];
            }
        }
        if(secondlargest == Integer.MIN_VALUE){
            System.out.println("second largest number of array : " + "-1");
        }else{
             System.out.println("second largest number of array : " + secondlargest);
        }
        sc.close();
    }
}