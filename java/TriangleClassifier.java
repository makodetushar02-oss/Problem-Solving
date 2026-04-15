import java.util.Scanner;

public class TriangleClassifier {

    public static String classifyTriangle(double a, double b, double c) {
        // Check for positive sides
        if (a <= 0 || b <= 0 || c <= 0) {
            return "Invalid: sides must be positive";
        }
        // Check triangle inequality
        if (a + b <= c || b + c <= a || a + c <= b) {
            return "Invalid: not a valid triangle";
        }

        if (a == b && b == c) {
            return "Equilateral";
        } else if (a == b || b == c || a == c) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Triangle Classifier ===");
        System.out.print("Enter side A: ");
        double a = sc.nextDouble();
        System.out.print("Enter side B: ");
        double b = sc.nextDouble();
        System.out.print("Enter side C: ");
        double c = sc.nextDouble();

        String result = classifyTriangle(a, b, c);
        System.out.println("Triangle type: " + result);

        sc.close();
    }
}