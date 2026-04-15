import java.util.*;

public class Deletes{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String n = sc.next();
        StringBuilder sb = new StringBuilder(n);
        System.out.println(sb);
        // insert a string 
        sb.insert(1, 'u');
        System.out.println(sb);
        //delete an string
        sb.delete(1,2);
        System.out.println(sb);
        sc.close();
    }
}