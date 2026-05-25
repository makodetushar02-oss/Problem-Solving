public class Problem35 {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 2},
                {3},
                {4, 5, 6}
        };
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i].length;
        }
        int[] result = new int[total];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                result[index++] = arr[i][j];
            }
        }
        for (int x : result) {
            System.out.print(x + " ");
        }
    }
}