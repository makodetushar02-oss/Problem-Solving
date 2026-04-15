abstract class ANIMALS{
  abstract void walk();
 ANIMALS(){
    System.out.println("YOU ARE CREATING AN ANIMAL");
  }
  public void eat(){
    System.out.println(" ANIMAL EATS ");
  }
}
class Horse extends ANIMALS{
    Horse(){
        System.out.println("you created a Horse");
    }
    public void walk(){
        System.out.println("horse can walk on two legs");
    }
}
class Hen extends ANIMALS {
    public void walk(){
        System.out.println("hen can walk on 4 legs");

    }
}
