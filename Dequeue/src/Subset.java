/*************************************************************************
 * Name: Wilbur de Souza
 * 
 * Compilation: javac Subset.java Execution: echo A B C D E F G H I | java
 * Subset 3 Dependencies: StdIn, StdOut
 * 
 * Description: Read N strings and print K of them in random order.
 * 
 *************************************************************************/

public class Subset {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) q.enqueue(StdIn.readString());
        for (int i = 0; i < k; i++)
            System.out.println(q.dequeue());
    }
}
