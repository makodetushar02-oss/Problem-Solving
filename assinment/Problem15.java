import java.util.*;

public class Problem15 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("enter the size of Array : ");
        int size = sc.nextInt();
        int[] belt = new int[size];

        for (int i = 0; i < size; i++) {
            belt[i] = sc.nextInt();
        }
        System.out.println("enter Rotational value :");
        int d = sc.nextInt();
        d = d % belt.length;
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = belt[(i + d) % size ];
        }
        for (int i = 0; i < size; i++) {
            System.out.print(result[i] + " ");
        }
        sc.close();
    }
}