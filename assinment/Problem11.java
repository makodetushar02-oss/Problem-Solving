import java.util.*;
public class Problem11 {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("enter the size of Array : ");
    int size = sc.nextInt();
    int nums[] = new int[size];

     for(int i = 0 ; i < size ; i++){
           nums[i] = sc.nextInt();
        }

        for(int j = 1; j < nums.length; j++){
            nums[j] =nums[j] + nums[j - 1] ;
        }
        System.out.println(Arrays.toString(nums));
        sc.close();
    }
}