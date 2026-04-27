public class chamnn {
    public static void main(String[] args) {
       String mystr = "Hello %s! 10 percentage is %,f bytes.";
        String Result = String.format(mystr, "my"  , 78.8) ;
        System.out.println(Result);

    }
}
