import java.util.Scanner;
public class Problem24 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N;
        System.out.print("Enter size of square matrix : ");
        N = sc.nextInt();
        int[][] matrix = new int[N][N];
        int sum = 0;
        System.out.println("Enter values for matrix : ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextInt();
                if (i == j) {
                    sum += matrix[i][j];
                }
                if (i + j == N - 1) {
                    sum += matrix[i][j];
                }
            }
        }
        if (N % 2 == 1) {
            int mid = N / 2;
            sum -= matrix[mid][mid];
        }
        System.out.println("Sum of diagonals : " + sum);
        sc.close();
    }
}