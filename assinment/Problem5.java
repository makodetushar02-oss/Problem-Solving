import java.util.*;
public class Problem5 {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("enter the size of Array : ");
    int size = sc.nextInt();
    int pixels[] = new int[size];

     for(int i = 0 ; i < size ; i++){
           pixels[i] = sc.nextInt();
        }
         int largest = 0;

        for(int j = 0; j < pixels.length; j++){
            if( pixels[j] != 0){
                int temp =pixels[j] ;
                pixels[j] = pixels[largest] ;
                pixels[largest] = temp ;
                largest++ ;
            }
        }
        System.out.println(Arrays.toString(pixels));
        sc.close();
    }
}