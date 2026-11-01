public class SecondSmallestElement{
public static void main(String[] args) {
    int[] arr = { 23 ,45 ,65 , 54 , 52, 47 };
    int n = arr[0];
    int largest = arr[1];
    for (int i = 2; i < arr.length; i++) {
        if(n < arr[i]){
            n = arr[i];
        }
        largest = Math.min(n , largest);
    }
    System.out.println("Second largest" + " " + largest);
}
}
