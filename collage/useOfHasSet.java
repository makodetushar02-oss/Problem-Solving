import java.util.HashSet;
import java.util.Set;

public class useOfHasSet{
public static void main(String[] args) {
    int[] arr = { 1, 2 ,2 ,3 ,4};
    System.out.println(containDuplicate(arr));
}
private static boolean containDuplicate(int[] arr){
    Set<Integer> set = new HashSet<>();
    for (int i : arr) {
        if(set.contains(i)) return true;
        set.add(arr[i]);
    }
    return false;

}
}