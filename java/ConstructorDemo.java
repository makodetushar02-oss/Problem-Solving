public class ConstructorDemo {

    int id;
    String name;

    // 1. Default Constructor
    ConstructorDemo() {
        System.out.println("Default Constructor Called");
    }

    // 2. Parameterized Constructor
    ConstructorDemo(int i, String n) {
        id = i;
        name = n;
    }

    // 3. Copy Constructor
    ConstructorDemo(ConstructorDemo obj) {
        id = obj.id;
        name = obj.name;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
    }

    public static void main(String[] args) {

        // Default Constructor
        ConstructorDemo c1 = new ConstructorDemo();

        System.out.println();

        // Parameterized Constructor
        ConstructorDemo c2 = new ConstructorDemo(101, "Tushar");

        System.out.println("Parameterized Constructor Output:");
        c2.display();

        System.out.println();

        // Copy Constructor
        ConstructorDemo c3 = new ConstructorDemo(c2);

        System.out.println("Copy Constructor Output:");
        c3.display();
    }
}