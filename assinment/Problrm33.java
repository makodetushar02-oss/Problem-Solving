public class Problrm33 {
    public static void main(String[] args) {
        int n = 4;
        int[][] tri = new int[n + 1][];

        for (int i = 0; i <= n; i++) {
            tri[i] = new int[i + 1];
            tri[i][0] = 1;
            tri[i][i] = 1;
            for (int j = 1; j < i; j++) {
                tri[i][j] = tri[i - 1][j - 1] + tri[i - 1][j];
            }
        }
        for (int i = 0; i <= n; i++) {

            for (int j = 0; j < tri[i].length; j++) {
                System.out.print(tri[i][j] + " ");
            }

            System.out.println();
        }
    }
}