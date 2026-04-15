class Using {
    String name ;
    int age ;

    public void printInfo() {
        System.out.println(this.name);
        System.out.println(this.age);
    }

    // Parameterized constructor
    Using(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Copy constructor
    Using(Using s2) {
        this.name = s2.name;
        this.age = s2.age;
    }

    Using() {
        // default constructor
    }
}

public class Copy {
    public static void main(String args[]) {
        Using s1 = new Using("TUSHAR", 18); // Using parameterized constructor
        Using s2 = new Using(s1);          // Using copy constructor

        s2.printInfo(

        );
    }
}
