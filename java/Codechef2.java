import java.util.*;


class Codechef2
{
	public static void main (String[] args) 
	{
		// your code goes here
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		while(T-- > 0){
		    int X = sc.nextInt();
		    if(X <= 20){
		        System.out.println(X * 10);
		    }
		    else{
		        System.out.println(200 +((X-20)/2)*5);
		    }
		}
        sc.close();
	}
}