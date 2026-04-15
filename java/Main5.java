import java.util.*;

class Main5 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        if ( x < y ){
            System.out.println("y is greater then x");
        }
        else{
             if ( x > y ){
            System.out.println("x is greater then y");
            }
            else {
            System.out.println(" equal ");
            }
        }
        sc.close();
    }
}