import java.util.*;

public class Assinment1 {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] arr = {4,5,1,2,1,4};
    for(int i=0;i< arr.length;i++){
            map.put(arr[i], map.getOrDefault(arr[i], 0)+1);
        }
        for (int i = 0; i < arr.length; i++) {
            if(map.containsKey(1)){
                System.out.println(map.va);
            }
        }
    }
}