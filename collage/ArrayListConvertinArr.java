import java.util.*;

public class ArrayListConvertinArr {
   public static void main(String[] args) {
      ArrayList<Integer> list = new ArrayList<>();
      list.add(10);
      list.add(20);
      list.add(30);
      list.add(40);
      list.add(50);
      System.out.println("befor list " + list);
      Object[] list2 = list.toArray();
      System.out.println("Elements of Arraylist as Array" + Arrays.toString(list2));

   }
}