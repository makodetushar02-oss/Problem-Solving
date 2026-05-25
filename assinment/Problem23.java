import java.util.Scanner;
public class Problem23 {
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m, n;
        System.out.print("Enter number of rows : ");
        m = sc.nextInt();
        System.out.print("Enter number of columns : ");
        n = sc.nextInt();
        int[][] matrix = new int[m][n];
        System.out.println("Enter values for matrix : ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int target;
        System.out.print("Enter value to find in matrix : ");
        target = sc.nextInt();
        int low = 0;
        int high = (m * n) - 1;
        boolean found = false;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int row = mid / n;
            int col = mid % n;
            int value = matrix[row][col];
            if (value == target) {
                found = true;
                break;
            }
            else if (value < target) {
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        if (found) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
        sc.close();
    }
}