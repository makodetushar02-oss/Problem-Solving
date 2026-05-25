import java.util.Scanner;

public class Problem31 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] arr = new int[n][];
        for (int i = 0; i < n; i++) {
            arr[i] = new int[i + 1];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = i * j;
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.print("Row" + i + ":[");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
                if (j != arr[i].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("] ");
        }
        sc.close();
    }
}