import java.util.*; 
 
public class Updatebit { 
   public static void main(String args[]) { 
    Scanner sc = new Scanner(System.in);
    int opper = sc.nextInt();
    int n = 5; //0101 
    int pos = 3; 
    int bitMask = 1<<pos ;
    if(opper == 1){
        // set bit
      int newNumber = bitMask | n; 
      System.out.println(newNumber); 
      }else{
        // clear bit
      int newbitMask = ~(bitMask);
      int newNumber = newbitMask ;
      System.out.println(newNumber);
      }
      sc.close();
    }
}