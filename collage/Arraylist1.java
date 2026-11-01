import java.util.*;


public class Arraylist1 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Tushar");
        list.add("XYZ");
        list.add("ABC");
        list.add("null");
        list.add("XYZ");
        System.out.println(list);
        // Traversing list through Iterator;
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
    
}