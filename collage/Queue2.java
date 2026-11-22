import java.util.*;

public class Queue2 {

    public static void main(String[] args) {
        PriorityQueue<Integer> qp = new PriorityQueue<>(Collections.reverseOrder());
        qp.add(30);
        qp.add(10);
        qp.add(20);
        System.out.println(qp);
        System.out.println(qp.peek());
        System.out.println(qp.poll());
        System.out.println(qp.poll());
        System.out.println(qp.poll());

    }
}