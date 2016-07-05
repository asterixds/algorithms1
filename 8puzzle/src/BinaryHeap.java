
public class BinaryHeap {

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
        }
     // print each key
        for (String s : pq) {
                System.out.println(s);
        }
        System.out.println();
    }
}
