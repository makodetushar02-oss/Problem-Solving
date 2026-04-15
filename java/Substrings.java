import java.util.*;
public class Substrings {
    public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
     String FirstName = sc.nextLine();
     int n1 = sc.nextInt();
     int n2 = sc.nextInt();
     String fullString =  FirstName.substring(n1 , n2);
     System.out.println( fullString);
     sc.close();
    }
}