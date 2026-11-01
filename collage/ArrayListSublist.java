import java.util.*;

public class ArrayListSublist {
   public static void main(String[] args) {
      ArrayList<String> list = new ArrayList<>();
      list.add("abc");
      list.add("efg");
      list.add("ijk");
      list.add("lmn");
      list.add("xyz");
      System.out.println("befor list " + list);
      List<String> sub = list.subList(1,4);
      System.out.println(sub);

   }
}