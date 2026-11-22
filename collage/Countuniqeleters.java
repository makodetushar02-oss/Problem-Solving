import java.util.HashSet;
import java.util.Set;

public class Countuniqeleters{
    public static void main(String[] args) {
        Set<Character> set = new HashSet<>();
        String s = "Hello";
        for(char ch : s.toCharArray()){
            set.add(ch);
        }
        System.out.println(set.size());
    }
}