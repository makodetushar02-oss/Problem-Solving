import java.util.*;

public class Problem13 {
    public static int removeDuplicates(int[] ids) {
        if (ids.length == 0) {
            return 0; 
        }

        int k = 1; 
        for (int i = 1; i < ids.length; i++) {
            if (ids[i] != ids[k - 1]) {
                ids[k] = ids[i];
                k++;
            }
        }
        return k;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[] ids = new int[size];

        for (int i = 0; i < size; i++) {
            ids[i] = sc.nextInt();
        }

        int k = removeDuplicates(ids);
        System.out.println("New Length: " + k);

        for (int i = 0; i < k; i++) {
            System.out.print(ids[i] + " ");
        }
        sc.close(); 
    }
}