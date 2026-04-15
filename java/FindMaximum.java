
public class FindMaximum{
public static void main(String[] args){
    
    int [] arr = {10 , 20 ,30 , 5 , 25 ,  15};
    int Max = arr[0];
    for( int i = 1 ; i< arr.length ; i++){
        if(arr[i] > Max){
            Max =arr[i];
        }
    }
    System.out.println("Mximum element :" + Max);
   }
}