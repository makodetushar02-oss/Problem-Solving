import java.util.*;

class Main6 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int butten = sc.nextInt();
        
        switch(butten){
            case 1 : System.out.println("hello bro");
            break;
            case 2 : System.out.println("namste bro");
            break;
            case 3 : System.out.println(" kasha ahe ");
            break;
            default : System.out.println(" INVALID INPUT ");
            
        }
        sc.close();
    }
}