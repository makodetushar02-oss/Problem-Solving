import java.util.Scanner;
public class Problem34 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rows = sc.nextInt();
        int[][] arr = new int[rows][];
        for (int i = 0; i < rows; i++) {
            int cols = sc.nextInt();
            arr[i] = new int[cols];
            for (int j = 0; j < cols; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        int[] rowMaxes   = new int[rows];   
        int overallMax   = Integer.MIN_VALUE;
        int rowPos       = 0;
        int colPos       = 0;
        for (int i = 0; i < rows; i++) {
            int rowMax = Integer.MIN_VALUE; 
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] > rowMax) {
                    rowMax = arr[i][j];
                }
                if (arr[i][j] > overallMax) {
                    overallMax = arr[i][j];
                    rowPos     = i;
                    colPos     = j;
                }
            }
            rowMaxes[i] = rowMax;   
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < rowMaxes.length; i++) {
            sb.append(rowMaxes[i]);
            if (i < rowMaxes.length - 1) sb.append(",");
        }
        sb.append("]");
        System.out.println(sb.toString());
        System.out.println("overall max=" + overallMax + " at (" + rowPos + "," + colPos + ")");

        sc.close();
    }
}