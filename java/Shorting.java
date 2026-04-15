// buble sort
import java.util.*;

class Shorting {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER SIZE OF ARRAY");
        int size = sc.nextInt();

        System.out.println("ENTER ELEMENTS OF ARRAY");
        int arr[] = new int[size];   // FIXED

        for (int i = 0; i<size; i++) {
            arr[i] = sc.nextInt();
        }

        // Bubble Sort
        for (int i = 0; i<arr.length - 1; i++) {
            for (int j = 0; j<arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println("SORTED ARRAY:");
        for (int i = 0; i<arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        sc.close();
    } 

}
