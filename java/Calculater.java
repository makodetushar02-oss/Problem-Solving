import java.util.*;

class Calculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: 1.Add  2.Subtract  3.Multiply  4.Divide");
        int num = sc.nextInt();

        System.out.println("Enter two numbers:");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();

        int result;

        switch (num) {
            case 1:
                result = num1 + num2;
                System.out.println("Sum = " + result);
                break;
            case 2:
                result = num1 - num2;
                System.out.println("Difference = " + result);
                break;
            case 3:
                result = num1 * num2;
                System.out.println("Product = " + result);
                break;
            case 4:
                if (num2 != 0) {
                    result = num1 / num2;
                    System.out.println("Quotient = " + result);
                } else {
                    System.out.println("Cannot divide by zero!");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }

        sc.close();
    }
}
