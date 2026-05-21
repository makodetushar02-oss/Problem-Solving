import java.util.*;
public class Problem16 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[] redemptions = new int[size];

        for (int i = 0; i < size; i++) {
            redemptions[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            sum += redemptions[i];
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        System.out.println(count);
        sc.close();
    }
}