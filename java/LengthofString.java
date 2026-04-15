import java.util.*;
public class LengthofString {
    public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
     String FirstName = sc.next();
     String lastName = sc.next();
     String fullString =FirstName + " " + lastName ;
     System.out.println( fullString.length());
     sc.close();
    }
}