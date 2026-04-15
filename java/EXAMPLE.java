import java.util.Scanner;
public class EXAMPLE {

    public static void main (String[] ar){          // ❌ 'string' should be 'String'
      Scanner s = new Scanner(System.in);           // ❌ 'system' should be 'System'
      System.out.println("enter first value") ;     // ❌ 'system' should be 'System' and missing semicolon not critical but wrong format
      int x = s.nextInt();
      System.out.println("enter second value");     // ❌ same 'system' issue
      int y = s.nextInt();
      int z ;
      z =x&y;                                       // ❌ variable 'z' not declared
      System.out.println("ANDing"+z);               // ❌ 'system' should be 'System'
      z=x|y;
      System.out.println("ORing"+z);
      z= x^y;
      System.out.println("X-ORing"+z);
      s.close();                                   // ❌ 'sc' should be 's'
    }
}
