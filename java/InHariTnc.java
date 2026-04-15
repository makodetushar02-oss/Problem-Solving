// hierachial Inharitance
import java.util.* ;
 
class Done {
    public void area(){
        System.out.println("Print Area");
    }
}
class Traingle extends Done{
    int l, h;
    public void area(){
        double area = 0.5 * l * h;
        System.out.println(area);
    }
}
class Circle extends Done {
    int r;
    public void area(){
        double area = 3.14 * r * r;
        System.out.println(area);
    }
}
public class InHariTnc{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter a number");
        int n = sc.nextInt();
    if( n % 2 == 1){
     Traingle t1 = new Traingle();
     System.out.println("enter length of traingle");
     t1.l = sc.nextInt();
     System.out.println("enter height of traingle");
      t1.h = sc.nextInt();
      System.out.println("area of traingle");
     t1.area();
     }else{
      Circle c1 = new Circle();
      System.out.println("enter radius of circle");
      c1.r = sc.nextInt();
      System.out.println("area of circle");
      c1.area();
     }
sc.close();
  }
}