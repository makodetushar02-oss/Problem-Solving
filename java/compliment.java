import java.util.Scanner;

class Compliment {
    public static void main(String[] ar) {
        int num;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the value of num");
        num = s.nextInt();

        // Compute and print the bitwise complement
        int result = ~num;
        System.out.println("Bitwise complement of " + num + " is: " + result);

        s.close();
    }
}