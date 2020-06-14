package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created by cenkc on 6/8/2020
 */
public class RandomizedQueue <Item> implements Iterable<Item> {

    private Item[] queue;
    private int N;
    private int last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        last = 0;
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (last == queue.length - 1) {
            resize(queue.length * 2);
        }
        queue[last] = item;
        last++;
        N++;
    }

    // remove and return a random item
    private void resize(int i) {
        Item[] tmp = (Item[]) new Object[i];
        int tmplast = 0;
        for (int j = 0; j < last; j++) {
            if (queue[j] != null) {
                tmp[tmplast] = queue[j];
                tmplast++;
            }
        }
        queue = tmp;
        last = tmplast;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        Item item = null;
        while (item == null) {
            int rand = StdRandom.uniform(last);
            item = queue[rand];
            queue[rand] = null;  // loitering !
        }
        N--;
        if ( N > 0 && N == queue.length/4 ) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        Item item = null;
        while (item == null) {
            int rand = StdRandom.uniform(last);
            item = queue[rand];
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int itemCount;
        private int index;
        private int[] rndOrder;

        public RandomizedQueueIterator() {
            itemCount = 0;
            rndOrder = new int[last];
            for (int j = 0; j < last; j++) {
                rndOrder[j] = j;
            }
            StdRandom.shuffle(rndOrder);
        }

        public boolean hasNext() {
            return itemCount < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = null;
            while (item == null) {
                item = queue[rndOrder[index]];
                index++;
            }
            itemCount++;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue;
        int i;

        queue = new RandomizedQueue<>();
        for (i = 0; i < 50; i++) {
            queue.enqueue(i);
        }

        i = queue.dequeue();
        StdOut.println("deque: " + i);

        for (i = 0; i < 5; i++) {
            StdOut.println("sample: " + queue.sample());
        }

        for (i = 0; i < 40; i++) {
            queue.dequeue();
        }

        queue = new RandomizedQueue<>();
        for (i = 0; i < 20; i++) {
            queue.enqueue(i);
        }

        for (int j : queue) {
            StdOut.println(j);
        }
    }
}
