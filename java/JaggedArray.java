//a jagged array is an array of arrays where ech sub array can have different sizes

public class JaggedArray{
    public static void main(String[] args){
        float [][] arr = new float [3][];
        arr[0] = new float[]{197668 ,2678924};
        arr[1] = new float[]{356385295 , 435843 ,54665738};
        arr[2] = new float[]{4858866};
        System.out.println("Jagged Array :");
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j< arr[i].length ; j++ ){
                System.out.print(arr[i][j]+ " ");
            }
            System.out.println();
        }

    }
}