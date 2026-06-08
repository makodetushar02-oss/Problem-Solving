class Accountent {
    void Ac() {
        System.out.println("I am employee in XY Bank");
    }

    void Salery() {
        System.out.println("My Salery is basic Starts from : 25000/- ");
    }
}

class Casier extends Accountent {
    void cash() {
        System.out.println("I manage the custmer transections");
    }

    void salery() {
        System.out.println("My Salery is basic Starts from : 40000/- ");
    }
}

class Manager extends Casier {
    void Man() {
        System.out.println("I manage the hole branch ");
    }

    void Msalery() {
        System.out.println("My Salery is basic Starts from : 70000/- ");
    }
}

public class Bank {
    public static void main(String[] args) {
        Manager M = new Manager();
        M.Ac();
        M.Salery();
        System.out.println();
        M.cash();
        M.salery();
        System.out.println();
        M.Man();
        M.Msalery();
    }
}
