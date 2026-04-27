import java.util.* ; 

public class MergeArray{
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] arr1 = new int[n];;
        int m = sc.nextInt();
        int [] arr2 = new int[m];
        for(int i = 0 ; i< n ; i++){
             
            arr1[i] = sc.nextInt();
        }
        for(int j = 0 ; j< m ; j++){
            arr2[j] = sc.nextInt();
        }
        int[] merged = new int[arr1.length + arr2.length ];
        System.arraycopy(arr1, 0, merged, 0, arr1.length);
        System.arraycopy(arr2, 0, merged,  arr1.length ,arr2.length);
        System.out.println("merged array : " + Arrays.toString(merged)  + " ");
        sc.close();
    }
}