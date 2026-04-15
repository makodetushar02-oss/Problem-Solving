interface  JANIMALS{
  public void walk();
  public void eat();
}
class Horrse implements  JANIMALS{
    public void eat(){
        System.out.println(" Horse eats grass");
    }
    public void walk(){
        System.out.println("horse can walk on two legs");
    }
}
 class Hens implements JANIMALS {
    public void walk(){
     System.out.println("hen can walk on 4 legs");
    }
       public void eat() {
       System.out.println("Hen eats grains");
       }
}
class Ops {
    public static void main(String args[]){
        Horrse h1 =  new Horrse();
       h1.walk();
       h1.eat();
       Hens h2 = new Hens();
       h2.walk();
       h2.eat();
    }
}