import java.util.PriorityQueue;

 class Queue3 {
    String name;
    int marks;
    Queue3( String name,int marks){
        this.name = name;
        this.marks = marks;
    }
}
 
public class Demo{
    public static void main(String[] args) {
        PriorityQueue<Queue3> Q = new  PriorityQueue<>((a,b) -> b.marks - a.marks);
        Q.add(new Queue3("Tushar", 89));
        Q.add(new Queue3("MBappu", 69));
        Q.add(new Queue3("Modino", 67));
        Q.add(new Queue3("RC", 39));
        while (!Q.isEmpty()) {
            Queue3 s = Q.poll();
            System.out.println(s.name + " " + s.marks);
        }
    }
}