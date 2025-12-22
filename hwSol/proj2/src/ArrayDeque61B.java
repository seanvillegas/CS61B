import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst = 4;
    private int nextLast = 5;
    static final int INEFFICIENT = 16;

    /**
     * Constructor
     */
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            copyToResize();
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            copyToResize();
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }
    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        ArrayList<T> blackBox = new ArrayList<>();

        if (isEmpty()) {
            return blackBox;
        }
        for (int index = 0; index < size; index++) {
            blackBox.add(get(index));
        }
        return blackBox;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (get(0) == null) {
            return null;
        }
        int firstIndex = (nextFirst + 1) % items.length;
        T returnFirst = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size--;

        if (checkUsageRatio()) {
            resizeDown();
        }

        return returnFirst;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (get(size - 1) == null) {
            return null;
        }
        T returnLast = get(size - 1);
        nextLast = (nextLast - 1 + items.length) % items.length;
        items[nextLast] = null;
        size--;

        if (checkUsageRatio()) {
            resizeDown();
        }

        return returnLast;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     * <p>
     * Note that you should return null if the index is larger than the size of your deque, not the underlying array.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        // thought: pass by value the array structure instead of hardcoding the `items` so its dynamic and re-use-able
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        }
        int userOrder = (this.nextFirst + 1 + index + items.length) % items.length;
        return items[userOrder];
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    // Helper Functions

    private T[] copyToResize() {
        int preAllocate = items.length * 2;
        T[] newArray = (T[]) new Object[preAllocate];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        items = newArray;
        nextFirst = items.length - 1;
        nextLast = size;
        return items;
    }

    /**
     * Helper function that returns boolean if Usage Ratio is less than optimal items.length space needed.
     * RTFA:
     * * For item.length of >= 16 use the formula: Usage factor <= 25% to resize the array down for
     * efficient memory use
     * * R(Usage Factor) = size / items.length <= 0.25
     */
    private boolean checkUsageRatio() {
        double usageRatio = (double) size / items.length;
        return items.length >= INEFFICIENT && usageRatio <= 0.25;
    }

    /**
     * Resize the array down when usage ratio is too low.
     */
    private T[] resizeDown() {
        int deAllocate = items.length / 2;
        T[] newArray = (T[]) new Object[deAllocate];
        // Copy elements in proper order using circular indexing
        // Do not rely on get(i) inside resizeDown(), because get(i) depends on old nextFirst
        for (int i = 0; i < size; i++) {
            int oldIndex = (nextFirst + 1 + i) % items.length;
            newArray[i] = items[oldIndex];
        }

        items = newArray;
        nextFirst = newArray.length - 1;
        nextLast = size;
        return items;
    }

    /**
     * returns an iterator (a.k.a. seer) into ME
     */
    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {

            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }

    }

    @Override
    /**
     * Use Deque61B so its compatible with other has a relationships of interface Deque61B
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Deque61B<?> otherDeque) {
            if (this.size != otherDeque.size()) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                T thisItem = this.get(i);
                Object otherItem = otherDeque.get(i);
                if (!thisItem.equals(otherItem)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Override the toString method in ArrayDeque61B.java
     */
    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            returnSB.append(get(i));
            returnSB.append(", ");
        }
        returnSB.append(get(size - 1));
        returnSB.append("]");
        // use toString to have the correct type (to get the string outside of the string builder)
        return returnSB.toString();
    }
}
