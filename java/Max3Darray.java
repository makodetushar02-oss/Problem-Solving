public class Max3Darray{
    public static void main(String[] args){
        int[][][] arr = {
            {
                {1,2,3},
                {4,5,6}
             },
            {
                {7,8,9},
                {10,11,12}
            }
        };
     int max = arr[0][0][0];
     for(int i = 0 ; i< arr.length ; i++){
        for(int j = 0 ; j< arr[i].length ; j++){
            for(int k = 0 ; k< arr[i][j].length ; k++){
              if( arr[i][j][k] > max){
                max = arr[i][j][k];
              }  
     }
        
     }
     }
     System.out.println("Maximum :" + max);
    }
}