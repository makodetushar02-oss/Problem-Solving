class Student{
    String name ;
    int age ;
    public void printInfo(){
        System.out.println(this.name);
        System.out.println(this.age);
    }
     Student(){
        System.out.println("constructor called");
     }
}
public class OOPSS {
    public static void main(String args[]){
        Student s1 = new Student();
        s1.name = "aarush" ;
        s1.age = 18 ;

        s1.printInfo();


    }
}