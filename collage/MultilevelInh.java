class Director {
    void director(){
        System.out.println("I am Director of Collage");
    }
}
class HOD extends Director {
    void hod() {
        System.out.println("I am your Head of Dipartment");
    }
}
class student extends HOD  {
    void S() {
        System.out.println("I am Student in TIT excellence");
    }
}
public class MultilevelInh {
    public static void main(String[] args) {  
        student s = new student();
        s.director();
        s.hod();
        s.S();
    }
}
