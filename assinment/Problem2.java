import java.util.* ;

public class Problem2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of array");
        int size = sc.nextInt();
        int temp[] = new int[size];

        for(int i = 0 ; i<size ; i++){
            temp[i] = sc.nextInt();
        }

        int max = temp[0];
        int mini = temp[0];

        for(int i = 1 ; i<temp.length ; i++){
            if(max < temp[i]){
               max = temp[i]  ; 
            }
            if( mini > temp[i]){
                mini = temp[i] ;
            }
        }
        System.out.println("the max temp is : " + max);
        System.out.println("the mini temp is : " + mini);
        sc.close();
    }
}