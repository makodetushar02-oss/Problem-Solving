import java.util.PriorityQueue;

public class SmallestElement {

    public static void main(String[] args) {
        PriorityQueue<Integer> qp = new PriorityQueue<>();
        qp.add(7);
        qp.add(10);
        qp.add(4);
        qp.add(3);
        qp.add(20);
        qp.add(50);
        System.out.println(qp);
    }
}