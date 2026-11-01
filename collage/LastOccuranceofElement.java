public class LastOccuranceofElement{
    public static void main(String[] args) {
        int[] arr = {12 , 3 ,2 , 4, 5 ,63, 2 ,65};
        int key = 2;
        int lastoccurence = -1;
        for (int i =arr.length - 1; i >= 0; i--) {
            if(arr[i] == key) {
                lastoccurence = i;
                break;
            }
        }
        System.out.println(lastoccurence);
    }
}