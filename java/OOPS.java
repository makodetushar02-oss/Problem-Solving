class Pen{ 
        String color ;
        String type ;
        public void write(){
        System.out.println("Write somthing");
        }
        public void printColor(){
        System.out.println( this.color );
        }
}
public class OOPS{
    public static void main(String args[]){
        Pen pen1 = new Pen();
        pen1.color = "blue";
        pen1.type = "ball";

        Pen pen2 = new Pen();
        pen2.color = "black";
        pen2.type ="gell";
        pen1.write();
        pen1.printColor();
        pen2.printColor();
    }
}