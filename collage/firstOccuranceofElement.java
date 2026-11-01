public class firstOccuranceofElement{
    public static void main(String[] args) {
        int[] arr = {12 , 3 ,2 , 4, 5 ,63, 65};
        int key = 2;
        int firstoccurence = -1;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == key) {
                firstoccurence = i;
                break;
            }
        }
        System.out.println(firstoccurence);
    }
}