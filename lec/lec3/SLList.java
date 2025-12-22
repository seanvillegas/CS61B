public class SLList {
    private IntNode first;
    private int size;

    private static class IntNode {
         int item;
         IntNode next;

         IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    // Creates empty list
    public SLList() {
        first = null;
        size = 0;
    }
    public SLList(int x) {
        first = new IntNode(x, null);
        size++;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
        size++;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Solved iteratively with while loop compared to recursion */
    public void addLast(int x) {
        size++;
        // Move p until it reaches end of list
        // This bugs out if there is no addFirst calls. You cant p.next on an already empty list.

        if (first == null) {
            first = new IntNode(x, null);
            return;
        }
        IntNode p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /** This method "speaks language of the gods" and interacts with the naked list */
    // it is computationally heavy and therefore just adding the count of each time you add an item will automatically
    //  increase the size
//    public static int size(IntNode p) {
//        if (p.next == null) {
//            return 1;
//        }
//        return 1 + size(p.next);
//    }
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        // L.addFirst(10);
        L.addLast(20);
        System.out.println(L.size());
        // L.addFirst(5);
        // System.out.println(L.getFirst());
    }
}