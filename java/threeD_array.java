public class threeD_array{
    public static void main(String [] args){
        int [][][] array = new int[2][3][4];
        int value = 1 ;
        for(int i =0 ; i<2 ; i++){
           for(int j =0 ; j<3 ; j++){
            for(int k =0 ; k<4 ; k++){
             array[i][j][k] = value++;
        }
        } 
        }
        for(int i =0 ; i<2 ; i++){
            System.out.println("plane "+ (i+1) +":");
           for(int j =0 ; j<3 ; j++){
            for(int k =0 ; k<4 ; k++){
                System.out.println(array[i][j][k] + ":");
             array[i][j][k] = value++;
        }
        System.out.println();
        } 
        System.out.println();
        }
    }
}