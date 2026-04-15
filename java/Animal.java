 abstract class Animal{

}
class Horse extends Animal{
    public void walk(){
        System.out.println("horse can walk on two legs");
    }
}
class Hen extends Animal{
    public void walk(){
        System.out.println("hen can walk on 4 legs");

    }
}
class Op{
    public static void main(String args[]){
        Horse h1 =  new Horse();
        h1.walk();

    }
}