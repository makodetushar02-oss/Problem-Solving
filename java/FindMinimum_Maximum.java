 import java.util.*;

public class FindMinimum_Maximum{
public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter size of the array");
    int size = sc.nextInt();
    int Number[] = new int[size];
    System.out.println("Enter the elements of the Array");
    for(int i = 0 ; i< size ; i++){
        Number[i] = sc.nextInt();
    }
    int Max = Integer.MIN_VALUE;
    int Min = Integer.MAX_VALUE;
    for(int i = 0 ; i < Number.length; i++){
        if( Number[i] < Min){
            Min = Number[i];
        }
        if(Number[i] > Max){
            Max = Number[i];
        }
    }
    System.out.println("Maximum value :" + Max);
    System.out.println("Minimum value :" + Min);
    sc.close();
   }
}