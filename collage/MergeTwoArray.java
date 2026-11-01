import java.util.*;
public class MergeTwoArray{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();
        int[] arr1 = new int[n1];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = sc.nextInt();
        }
        int n2 = sc.nextInt();
        int[] arr2 = new int[n2];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = sc.nextInt();
        }
        int[] Merge = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++) {
            Merge[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            Merge[arr1.length +i] = arr2[i];
        }
        sc.close();
    }
}