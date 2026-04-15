import java.util.Arrays;

public class SortArray{
    public static void main(String[] args){
        int[] arr = { 50,35, 76, 90, 12 };
        Arrays.sort(arr);
        System.out.println("Sorted Array :");
        for( int i = 0 ; i<arr.length; i++){
            System.out.println(arr[i] + " ");
        }
    }
}