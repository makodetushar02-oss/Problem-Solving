import java.util.*;
public class CompareToArray{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isEqual = false;
        int n1 = sc.nextInt();
        int[] arr1 = new int[n1];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = sc.nextInt();
        }
        int n2 = sc.nextInt();
        int[] arr2 = new int[n2];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = sc.nextInt();
        }
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i] ){
                isEqual = false ;
            }
            else isEqual = true ;
        }
        System.out.println(isEqual);
        sc.close();
    }
}