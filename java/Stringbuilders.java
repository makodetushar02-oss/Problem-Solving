import java.util.*;

class Stringbuilders {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter a sentance");
        String N = sc.nextLine();
        StringBuilder sb = new StringBuilder(N);
        System.out.println(sb);
        // string at index
        System.out.println("enter index");
        int X = sc.nextInt();
        System.out.println(sb.charAt( X ));
        //set charecter at any index
        System.out.println("enter a index which have to replace");
        int z =sc.nextInt();
        System.out.println("enter a charecter");
        char y =sc.next().charAt(z); 
        sb.setCharAt ( z, y );
        System.out.println(sb);
        sc.close();
    }
}