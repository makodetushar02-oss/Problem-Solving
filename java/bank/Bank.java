package bank ;
class Account{
   public String name ;
   protected String email ;
   private String password ;

   public String getpassword(){
   return this.password;
   }
   public void setpassword(String pass){
    this.password = pass ;

   }
}
public class Bank{
    public static void main(String args[]){
        Account A1 = new Account();
        A1.name =" tushar makode";
        A1.email = "tusharmakode@gmail.com" ;
        A1.setpassword("1234fn5uth8");
        System.out.println("Name     : " + A1.name);
        System.out.println("Email    : " + A1.email);
        System.out.println("Password : " + A1.getpassword());

    }
}