import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    private class Node {
        Node prev;
        T item;
        Node next;

        public Node(T i, Node n) {
            item = i;
            next = n;
        }
    }

    /** Create empty LinkedListDeque61B. This is the constructor */
    public LinkedListDeque61B() {
        sentinel = new Node(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        // newNode                  // oldNode
        sentinel.next = new Node(x, sentinel.next);
        // set newNode to dummy sentinel
        sentinel.next.prev = sentinel;
        // set oldNode.prev to the newNode object
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        // create pointer to oldNode
        Node oldNode = sentinel.prev;
        // create the newNode
        Node newNode = new Node(x, oldNode);
        // rewire
        newNode.prev = oldNode;
        oldNode.next = newNode;
        // assign sentinel.prev to point to newNode
        sentinel.prev = newNode;
        // keep circular structure
        newNode.next = sentinel;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     *
     * allowed to use java.util.List data structure
     * Do not call this method in any other method for dumb challenge
     */
    @Override
    public List<T> toList() {
        // this method will take o(n) time and is only way I know of how to traverse everything
        List<T> returnList = new ArrayList<>();
        if (size() == 0) {
            return returnList;
        }

        Node pointer = sentinel.next;
        while (pointer != sentinel) {
            returnList.add(pointer.item);
            pointer = pointer.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        // could also just check size and if its 0 return true else false
        return sentinel.next == sentinel;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node oldFirst = sentinel.next;
        T returnMe = oldFirst.item;
        Node rePointNode = oldFirst.next;
        sentinel.next = rePointNode;
        rePointNode.prev = sentinel;
        size--;

        return returnMe;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node oldLast = sentinel.prev;
        T returnMe = oldLast.item;
        Node newLast = oldLast.prev;
        sentinel.prev = newLast;
        //newLast.prev = oldLast.prev.prev; // newLast.prev.prev bugs out // this line is buggy
        newLast.next = sentinel;
        size--;

        return returnMe;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index >= size() || index < 0 || isEmpty()) {
            return null;
        }

        Node pointer = sentinel.next;
        for (int idx = 0; idx < index; idx++) {
            pointer = pointer.next;
        }

        return pointer.item;


    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index >= size() || index < 0 || isEmpty()) {
            return null;
        }
        return recurseHelper(index, sentinel.next);
    }

    private T recurseHelper(int index, Node currNode) {
        if (index == 0) {
            return currNode.item;
        } else {
            return recurseHelper(index - 1, currNode.next);
        }
    }
}
