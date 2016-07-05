/*************************************************************************
 * Name: Wilbur de Souza
 *
 * Compilation:  javac Deque.java
 * Execution:
 * Dependencies: java.util.Iterator
 *
 * Description: Deque implementation
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n = 0; // number of elements
    private Node first = null;
    private Node last = null;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("No item to add");
        Node temp = first;
        first = new Node();
        first.item = item;
        first.next = temp;
        if (isEmpty()) {
            last = first;
        } else {
            temp.prev = first;
        }
        n++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("No item to remove");
        Node temp = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            temp.next = last;
            last.prev = temp;
        }

        n++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        Node tmp = first;
        first = first.next;
        --n;
        if (isEmpty()) {
            last = null;
            first = null;
        }
        else if (n == 1) {
            last.prev = null;
            last.next = null;
            first.next = null;
            first.prev = null;
        } else
            first.prev = null;
        return tmp.item;
    }
    

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node tmp = last;
        last = last.prev;

        --n;
        if (isEmpty()) {
            first = null;
            last = null;
        }
            
        else if (n == 1) {
            last.prev = null;
            last.next = null;
            first.next = null;
            first.prev = null;
        } else
            last.next = null;
        return tmp.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DQIterator();
    }

    private class Node {
        private Item item = null;
        private Node next = null;
        private Node prev = null;
    }

    private class DQIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        /*
         * int n = 100; for (int k = 1; k <= n; k=k*2) { Deque<Integer> d = new
         * Deque<Integer>(); for (int i=0; i< 1024*k ;i++) { d.addLast(new
         * Integer(i));
         * 
         * } System.out.println(1024*k + " : " +
         * (double)ObjectSizeFetcher.deepSizeOf(d)/(1024*k)); }
         * 
         * Deque<String> d = new Deque<String>(); d.addFirst("A"); int size =
         * d.size(); assert size == 1; String tmp = d.removeLast(); size =
         * d.size(); assert size == 0; assert tmp.equals("A"); d.addFirst("B");
         * d.removeFirst(); size = d.size(); assert size == 0; d.addFirst("A");
         * d.addFirst("B"); d.addFirst("C"); d.addLast("X"); for (String s : d)
         * { StdOut.print(" " + s); } StdOut.println();
         * 
         * Iterator<String> iter = d.iterator(); while (iter.hasNext()) { tmp =
         * (String) iter.next(); StdOut.print(" " + tmp); } StdOut.println();
         */
    }
}
