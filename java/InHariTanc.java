//multi level traingle
import java.util.* ;
 
class Domane {
    public void area(){
        System.out.println("Print Area");
    }
}
class Traingle extends Domane{
    int l, h;
    public void area(){
        double area = 0.5 * l * h;
        System.out.println(area);
    }
}
class Equiletraltraigale extends Traingle {
    int l, h;
    public void area(){
        double area = 0.5 * l * h;
        System.out.println(area);
    }
}
public class InHariTanc{
    public static void main(String args[]){
    Equiletraltraigale t1 = new Equiletraltraigale();
    Scanner sc = new Scanner(System.in);
    System.out.println("enter length of traingle");
    t1.l = sc.nextInt();
    System.out.println("enter height of traingle");
    t1.h = sc.nextInt();
    t1.area();
    sc.close();
    }
}