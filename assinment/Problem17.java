import java.util.*;
public class Problem17 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[] votes = new int[size];

        for (int i = 0; i < size; i++) {
            votes[i] = sc.nextInt();
        }
        int candidate = 0;
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (count == 0) {
                candidate = votes[i];
            }
            if (votes[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        System.out.println(candidate);
        sc.close();
    }
}