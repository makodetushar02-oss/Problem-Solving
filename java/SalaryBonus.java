import java.util.Scanner;

public class SalaryBonus {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Salary Bonus Calculator ===");
        System.out.print("Enter your salary: ");
        double salary = sc.nextDouble();

        double bonusPercent;
        double bonusAmount;
        double totalSalary;

        if (salary < 20000) {
            bonusPercent = 20;
        } else if (salary <= 50000) {
            bonusPercent = 15;
        } else {
            bonusPercent = 10;
        }

        bonusAmount = (bonusPercent / 100) * salary;
        totalSalary = salary + bonusAmount;

        System.out.println("\n------- Salary Details -------");
        System.out.println("Basic Salary   : Rs. " + salary);
        System.out.println("Bonus %        : " + bonusPercent + "%");
        System.out.println("Bonus Amount   : Rs. " + bonusAmount);
        System.out.println("Total Salary   : Rs. " + totalSalary);
        System.out.println("------------------------------");

        sc.close();
    }
}
