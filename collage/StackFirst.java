import java.util.Iterator;
import java.util.Stack;

public class StackFirst{
    public static void main(String[] args) {
        Stack<String> st = new Stack<>();
        st.push("fjvb");
        st.push("fhfjf");
        st.push("jnvkn");
        st.push("fsijvnjv");
        System.out.println(st.pop());
        Iterator<String> itr = st.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}