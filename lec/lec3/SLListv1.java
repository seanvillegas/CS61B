public class SLListv1 {
    private SLListv1.IntNode first;
    private int size;

    private static class IntNode {
        int item;
        SLListv1.IntNode next;

        IntNode(int i, SLListv1.IntNode n) {
            item = i;
            next = n;
        }
    }

    /** The first item (if it exists) is at sentinel.next. */
    private IntNode sentinel;

    /** Creates empty SLList. */
    // faithful companion. think of it as the middleman to your naked data structure
    public SLListv1() {
        sentinel = new IntNode(63,null);
        size = 0;
    }

    public SLListv1(int x) {
        sentinel = new IntNode(63,null);
        // this is the first node for your naked data structure 
        sentinel.next = new IntNode(x, null);
        size++;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size++;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    /** Solved iteratively with while loop compared to recursion */
    public void addLast(int x) {
        IntNode p = sentinel;
        size++;

        while (p.next != null) {
            p = p.next;
        }
        p.next = new SLListv1.IntNode(x, null);
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        SLListv1 L = new SLListv1(15);
        L.addFirst(10);
        L.addLast(20);
        System.out.println(L.size());
        L.addFirst(5);
        // System.out.println(L.getFirst());
    }
}
