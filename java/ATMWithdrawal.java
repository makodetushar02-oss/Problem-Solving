import java.util.Scanner;

public class ATMWithdrawal {

    // Predefined card details
    static final String VALID_CARD = "9131474715";
    static final String VALID_PIN  = "161076";
    static final double BALANCE    = 15000000.00;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

     
        
        System.out.print("Enter Card Number: ");
        String card = sc.nextLine();

        if (!card.equals(VALID_CARD)) {
            System.out.println("\n Invalid Card! Please use a valid card.");
            sc.close();
            return;
        }

        System.out.println(" Card Accepted!\n");

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        if (!pin.equals(VALID_PIN)) {
            System.out.println("\n Incorrect PIN! Transaction cancelled.");
            sc.close();
            return;
        }

        System.out.println(" PIN Verified!\n");

        // Step 3: Show Balance & Enter Withdrawal Amount
        System.out.println(" Available Balance: " + BALANCE);
        System.out.print("Enter Withdrawal Amount: ₹");
        double amount = sc.nextDouble();

        // Step 4: Check Balance & Process Withdrawal
        if (amount <= 0) {
            System.out.println("\n Invalid Amount! Enter a positive value.");
        } else if (amount > BALANCE) {
            System.out.println("\n Insufficient Balance!");
            System.out.println("   Available Balance: " + BALANCE);
        } else {
            double remaining = BALANCE - amount;
            System.out.printf( "  Withdrawn  : %-13.2f 9%n",  amount);
            System.out.printf( "  Remaining  : %-13.2f 9%n", remaining);
            System.out.println("\n  Please collect your cash. Thank you!");
        }

        sc.close();
    }
}
