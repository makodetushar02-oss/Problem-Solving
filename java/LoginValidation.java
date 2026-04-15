import java.util.Scanner;

public class LoginValidation {

    
    static final String VALID_USERNAME = "Tushar_02";
    static final String VALID_PASSWORD = "0191EC241030";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Login System ===\n");

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        if (username.equals(VALID_USERNAME)) {

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (password.equals(VALID_PASSWORD)) {
                System.out.println("\n Login Successful! Welcome, " + username);
            } else {
                System.out.println("\n Invalid Credentials!");
            }

        } else {
            System.out.println("\n Invalid Credentials!");
        }

        sc.close();
    }
}
