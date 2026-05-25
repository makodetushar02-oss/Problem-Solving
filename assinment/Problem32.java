
public class Problem32 {
    public static void main(String[] args) {
         int[][] matrix = {
                {1, 2},
                {3, 4, 5},
                {6}
        };
        int[] result = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {

            int sum = 0;
            
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
            result[i] = sum;
        }
        for (int x : result) {
            System.out.print(x + " ");
        }
    }
}