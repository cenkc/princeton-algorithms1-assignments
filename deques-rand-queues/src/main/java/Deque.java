package main.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created by cenkc on 6/3/2020
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {
        N = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
//        return first == null;
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        // similar to stack.push
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        // similar to queue.enqueue
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        // similar to stack.pop and queue.dequeue
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) {
            last = null;   // to avoid loitering
        } else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        final Item item = last.item;
        last = last.prev;
        N--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Item item;
        Node prev;
        Node next;

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("first1");
        deque.addFirst("first2");
        deque.addLast("last1");
        deque.addLast("last2");
        printDeque(deque);
        deque.removeLast();
        deque.removeFirst();
        printDeque(deque);
    }

    private static void printDeque(Deque deque) {
        final Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("--------------------");
    }
}
