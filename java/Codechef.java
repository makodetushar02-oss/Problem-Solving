import java.util.*;


class Codechef
{
	public static void main (String[] args) 
	{
		// your code goes here
		Scanner sc = new Scanner(System.in);
		int X = sc.nextInt();
		int Y = sc.nextInt();
		int Z = sc.nextInt();
		if(Y <= X+Z){
		    System.out.println(X + Z + 1 -Y);
		}
		else{
		    System.out.println("0");
		}
        sc.close();
	}
}
