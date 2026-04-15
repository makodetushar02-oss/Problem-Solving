
import java.util.Scanner; // Corrected: java.util.* is fine, but Scanner is more specific.

public class Addition {
    public static void main(String[] args) { // Corrected: 'String' must be capitalized, added a space after 'main', fixed the curly brace placement.
        Scanner sc = new Scanner(System.in); // Corrected: 'Scanner' and 'System' must be capitalized.
        int a, b,sum;
        System.out.print("Enter a: "); // Corrected: 'System' must be capitalized, added a colon for better prompt.
        a = sc.nextInt(); // Corrected: Added a semicolon (;)
        System.out.print("Enter b: "); // Corrected: 'System' must be capitalized, added a colon.
        b = sc.nextInt();
        sum = a + b;
        System.out.println("Sum of a and b is " + sum); // Corrected: 'System' must be capitalized.
        sc.close();
    }
}