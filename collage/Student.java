public class Student {
    String name;
    int age;
}

class Habibi extends Student {
    void Building() {
        System.out.println("you are in TIT-Ex");
    }
    void Enroll() {
        System.out.println("0191EC241030");
    }
}
class inh {
    public static void main(String[] args) {  
        Habibi c = new Habibi();
        c.Building();
        c.age = 20;
        c.name = "Tushar";
        c.Enroll();
        System.out.println("name = "+ c.name );
        System.out.println("age =" + c.age);
    }
}
