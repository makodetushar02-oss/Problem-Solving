class Done {
    String name ;
    int age ;

    public void printInfo(String name, int age) {
        System.out.println(name + " "+ age);
       
    }
    public void printInfo(String name ) {
        System.out.println(name);
       
    }
    public void printInfo( int age) {
        System.out.println( age);
       
    }
    
}

public class CopyPast {
    public static void main(String args[]) {
        Done s1 = new Done(); // Using parameterized constructor
        s1.name = "Tushar" ;
        s1.age = 20 ;
        s1.printInfo(s1.name+" "+s1.age);
    }
}
