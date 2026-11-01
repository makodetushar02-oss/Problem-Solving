import java.util.ArrayList;

public class ArrayListCloneMethode {
 public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);
    list.add(50);
    System.out.println("befor list " + list);
    ArrayList list2 = new ArrayList();
    list2 = (ArrayList)list.clone();
    System.out.println("After list " + list2);
 }
    
 }