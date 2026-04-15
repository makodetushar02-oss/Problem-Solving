import java.util.*;
public class charAtm {
    public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
     String FirstName = sc.next();
     String lastName = sc.next();
     String fullString = FirstName + " " + lastName ;
     System.out.println( fullString.length());
     for( int i =0 ; i<=fullString.length() ; i++ ){
        System.out.println(fullString.charAt(i));
        }
        sc.close();
    }
}