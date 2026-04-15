import java.util.*;

public class ATM_withdrawal_using_switch{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("ENTER CARD DETAILS : ");
        String VALID_CARD = sc.nextLine();
        String VALID_PIN  = sc.nextLine();
        double BALANCE    = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.println("Enter Card number :");
        String card = sc.nextLine();
        String cardStatus = card.equals(VALID_CARD) ? "valid" : "invalid";

        switch (cardStatus) {
            case "invalid":
                System.out.println("\nINVALID CARD\nPlease enter a valid card");
                sc.close();
                return;

            case "valid":
                System.out.println("\nCard Accepted\n");
                System.out.println("\nEnter PIN : ");
                String PIN = sc.nextLine();
                String pinStatus = PIN.equals(VALID_PIN) ? "valid" : "invalid";

                switch (pinStatus) {
                    case "invalid":
                        System.out.println("\nENTERED PIN IS WRONG\nPlease enter correct pin");
                        sc.close();
                        return;

                    case "valid":
                        System.out.println("\nSelect option ");
                        System.out.println("1. Cash Withdrawal");
                        System.out.println("2. Balance Enquiry");
                        System.out.println("3. Exit");
                        System.out.print("Enter choice : ");
                        int choice = sc.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("\nAVAILABLE BALANCE : " + BALANCE);
                                System.out.print("Enter Withdrawal Amount : ");
                                double amount = sc.nextDouble();

                                String withdrawStatus;
                                if (amount <= 0) {
                                    withdrawStatus = "INVALID_AMOUNT";
                                } else if (amount > BALANCE) {
                                    withdrawStatus = "INSUFFICIENT";
                                } else {
                                    withdrawStatus = "SUCCESS";
                                }

                                switch (withdrawStatus) {
                                    case "INVALID_AMOUNT":
                                        System.out.println("ENTER VALID AMOUNT");
                                        break;
                                    case "INSUFFICIENT":
                                        System.out.println("\nINSUFFICIENT BALANCE\nAVAILABLE BALANCE IS : " + BALANCE);
                                        break;
                                    case "SUCCESS":
                                        BALANCE -= amount;
                                        System.out.println("Withdrawal Successful");
                                        System.out.println("Withdrawn : " + amount);
                                        System.out.println("Remaining : " + BALANCE);
                                        break;
                                    default:
                                        System.out.println("Unknown Error!");
                                }
                                break;

                            case 2:
                                System.out.println("\nBALANCE ENQUIRY\nAvailable Balance is : " + BALANCE);
                                break;

                            case 3:
                                System.out.println("\nTHANK YOU FOR USING OUR ATM");
                                break;

                            default:
                                System.out.println("INVALID CHOICE\nPLEASE SELECT 1, 2 OR 3");
                        }
                        break;
                }
                break;
        }
        sc.close();
    }
}