import java.util.Scanner;

 public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String S = sc.next();  // read the string
        
        if (S.charAt(0) == S.charAt(2) && S.charAt(1) == 'w') {
            System.out.println("Cute");
        } else {
            System.out.println("No");
        }
        
        sc.close();
    }
}


