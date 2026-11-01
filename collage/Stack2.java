import java.util.*;

public class Stack2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Stack<Character> st = new Stack<>();
        for(char ch : s.toCharArray()){
            st.push(ch);
        }
        int count = 0;
        while(!st.isEmpty()){
        if(st.peek() == '(' || st.peek() == ')' || st.peek() == '{'|| st.peek() == '}' ){
            count++;
        }
        st.pop();
        }
        System.out.println(count % 2 == 0 ? "Balanced" : " Unbalanced");
        sc.close();
    }
}