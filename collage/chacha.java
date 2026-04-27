public class chacha {
    public static void main(String[] args) {
       String mystr = "Hello %s! One kilobyte is %,d bytes.";
        String Result = String.format(mystr, "world" , 1024) ;
        System.out.println(Result);

    }
}
