import java.util.*;

class Main4 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        if ( x % 2 == 0 ){
            System.out.println("ENTERED NUMBER IS EVEN");

        }
        else{
            System.out.println(" ENTERED NUMBER IS ODD ");

        }
        sc.close();
    }
}