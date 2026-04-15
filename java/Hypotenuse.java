import java.util.*;

public class Hypotenuse{
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        double a =sc.nextInt();
        double b =sc.nextInt();
        double hyp = Math.sqrt(a*a + b*b);
        System.out.println("Hypotenuse :" + hyp);
        sc.close();
    }
}