package src;

/**
 * 1. We need to let it store more than 100
 * 2. Make AList generic
 *      a. you must change every occurrence of int items to T[]
 *      b. cast Object back to T[]
 *      c. return methods must be T
 * 3. Optimize AList speed
 *      a. preAllocate on resize operation when user calls this method to make adding memory efficient
 */
public class AList<T> {

    //private int[] items;
    private T[] items;
    private int size;

    public AList() {
        //items = new int[100];
        items = (T []) new Object[8];
        size = 0;
    }

    private void resize(int capacity) {
        int preAllocate = size * 2;
        T[] a = (T []) new Object[preAllocate];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addLast(T x) {
        if (size == items.length) {
            resize(size++);
        }
        items[size] = x;
        size++;
    }

    public T getLast() {
        return items[size--]; 
    }

    public T get(int i) {
        return items[i];
    }

    public int size() {
        return size;
    }

    public T removeLast() {
        T x = items[size--];
        items[size--] = null;
        size--; 
        return x;
    }

    public static void main(String[] args) {
        System.out.println("200");
    }
}