class Students{
    String name ;
    int age ;
    public void printInfo(){
        System.out.println(this.name);
        System.out.println(this.age);
    }
     Students(String name ,int age){
        this.name = name ;
        this.age = age ;
     }
}
public class Trust {
    public static void main(String args[]){
        Students s1 = new Students( "TUSHAR" , 18);

        s1.printInfo();
    }
}