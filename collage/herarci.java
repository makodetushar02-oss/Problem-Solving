class Employee {
    void employee() {
        System.out.println("I am Employee At ABC compani");
    }
}

class Dev extends Employee {
    void Dev() {
        System.out.println("I am Devloper ");
    }
}

class Tester extends Employee {
    void Test() {
        System.out.println("I am code Tester");
    }
}

public class herarci {
    public static void main(String[] args) {
        Dev D = new Dev();
        Tester T = new Tester();
        D.employee();
        D.Dev();
        T.employee();
        T.Test();
    }
}
