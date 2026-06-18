interface Emp {
	void  work() ;
}

interface Boss {
	void  bull() ;
}
class slry implements Emp, Boss {
   void work(){
     System.out.println("I am a developer at ABC Company");
	}
	void bull(){
		System.out.println("i am the boss");
	}
	void E_S() {
		System.out.println(" Salary : 40000");
	}

	void B_S() {
		System.out.println(" Salary : 80000");
	}
}

public class MultipleInh {
	public static void main(String args[]) {
		slry M = new slry();
		M.work();  
		M.E_S();
		M.bull();
		M.B_S();
	}
}