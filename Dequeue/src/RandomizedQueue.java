/*************************************************************************
 * Name: Wilbur de Souza
 *
 * Compilation:  javac RandomizedQueue 1 2 3 4 5 6 7 - - - 3 4 5 9 8 .java
 * Execution:
 * Dependencies: java.util.Iterator
 *
 * Description: A Deque implementation
 *
 *************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n && capacity > 0;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("No item to enqueue");
        if (n == items.length) {
            resize(n << 1);
        }
        items[n++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Empty queue");
        exchange(items, StdRandom.uniform(n), --n);
        Item r = items[n];
        items[n] = null;
        if (n * 4 < items.length && items.length > 1)
            resize(items.length >> 1);
        return r;
    }

    // Swap entries i & j in the array a.
    private void exchange(Item[] a, int i, int j) {
        if (i == j)
            return;
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Empty queue");
        return items[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i = n;
        private int[] r;

        public RandomizedQueueIterator() {
            r = new int[i];
            for (int k = 0; k < i; k++)
                r[k] = k;
            StdRandom.shuffle(r);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[r[--i]];
        }
    }

    // unit testing
    public static void main(String[] args) {
        int n = 100;
        for (int k = 1; k <= n; k = k * 2) {
            RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
            for (int i = 0; i < 1024 * k; i++) {
                d.enqueue(Integer.valueOf(i));
                if (i % 2 == 0)
                    d.dequeue();
            }
            System.out.println(d.size());
        }
    }
}
