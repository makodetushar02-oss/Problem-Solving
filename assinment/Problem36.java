public class Problem36 {
    public static void main(String[] args) {
        int[][] A = {
                {1, 3},
                {2}
        };
        int[][] B = {
                {2, 4},
                {1, 5}
        };
        int[][] C = new int[A.length][];
        for (int i = 0; i < A.length; i++) {
            C[i] = new int[A[i].length + B[i].length];
            int p1 = 0, p2 = 0, k = 0;
            while (p1 < A[i].length && p2 < B[i].length) {

                if (A[i][p1] < B[i][p2]) {
                    C[i][k++] = A[i][p1++];
                } else {
                    C[i][k++] = B[i][p2++];
                }
            }
            while (p1 < A[i].length) {
                C[i][k++] = A[i][p1++];
            }
            while (p2 < B[i].length) {
                C[i][k++] = B[i][p2++];
            }
        }
        for (int[] row : C) {
            for (int x : row) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}