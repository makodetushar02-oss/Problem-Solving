import java.util.* ;

public class Problem14 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        
        int[] codes = new int[size];
        for(int i = 0 ; i < size; i++){
            codes[i] = sc.nextInt();
        }
        boolean sorted = true;
        for(int i = 0; i< size -1 ; i++){
           if( codes[i] > codes[i +1]){
            sorted = false;
           }
        }
        System.out.println(sorted);
        sc.close();
    }
}