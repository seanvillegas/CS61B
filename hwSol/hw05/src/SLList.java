public class SLList {
    private static class IntNode {
        int item;
        IntNode next;

        IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    /** The first item (if it exists) is at sentinel.next. */
    private IntNode sentinel;
    private int size;


    /** Creates empty SLList. */
    // faithful companion. think of it as the middleman to your naked data structure
    public SLList() {
        sentinel = new IntNode(0, null);
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntNode(0, null);
        // this is the first node for your naked data structure
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size = size + 1;
    }

    /** Solved iteratively with while loop compared to recursion */
    public void addLast(int x) {
        size = size + 1;
        IntNode p = sentinel;

        while (p.next != null) {
            p = p.next;
        }
        p.next = new SLList.IntNode(x, null);
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }


    public int size() {
        return size;
    }

    /** Stack.java method helpers */
    public void removeTop() {
        size--;
        sentinel.next = sentinel.next.next;
    }

    /** Returns item at index idx */
    public int getItem(int idx) {
        IntNode p = sentinel.next;
        for (int i = 0; i < idx; i++) {
            p = p.next;
        }
        return p.item;
    }
}
