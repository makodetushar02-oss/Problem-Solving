import java.util.*;

public class CompareStr {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = sc.next();

        System.out.print("Enter last name: ");
        String lastName = sc.next();

        // Combine into full name
        String fullName = firstName + " " + lastName;
        System.out.println("Full name: " + fullName);
        System.out.println("Length of full name: " + fullName.length());

        // Compare strings (case-insensitive)
        if (firstName.equalsIgnoreCase(lastName)) {
            System.out.println("Strings are equal");
        } else {
            System.out.println("Strings are not equal");
        }

        sc.close();
    }
}