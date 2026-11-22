import java.util.PriorityQueue;

public class PriorityQueueDemo {

    public static void main(String[] args) {
        PriorityQueue<Integer> que = new PriorityQueue<>();
        for (int i = 0; i < 3; i++) {
            que.add(i);
            que.add(1);
        }
        System.out.println(que);
    }
}