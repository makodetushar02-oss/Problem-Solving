import java.util.*;
public class Problem22 {
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        int top    = 0;
        int bottom = matrix.length - 1;
        int left   = 0;
        int right  = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++; 
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--; 
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;           
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int column = sc.nextInt();
        int row = sc.nextInt();
        int[][] matrix = new int[column][row];
        for(int i = 0; i< column; i++){
            for(int j = 0 ; j< row; j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        System.out.println("Example 1: " + spiralOrder(matrix));
        sc.close();
    }
}
