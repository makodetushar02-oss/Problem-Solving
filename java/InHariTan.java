// single level  inharitance
import java.util.* ;
 
class Shape {
    public void area(){
        System.out.println("Print Area");
    }
}
class Traingle extends Shape{
    int l, h;
    public void area(){
        double area = 0.5 * l * h;
        System.out.println(area);
    }
}
public class InHariTan{
    public static void main(String args[]){
    Traingle t1 = new Traingle();
    Scanner sc = new Scanner(System.in);
    System.out.println("enter length of traingle");
    t1.l = sc.nextInt();
    System.out.println("enter height of traingle");
    t1.h = sc.nextInt();
    t1.area();
    sc.close();
    }
}