public class Stack {
    SLList mySLL = new SLList();

    /** Operations **/

    /** Put x on top of stack with just an interface and method signature */
    public void push(int x) {
        // puts x on top of stack
        mySLL.addFirst(x);
    }

    /** Removes and returns top item on stack. Assume stack is never empty */
    public int pop() {
        int toRemove = mySLL.getFirst();
        mySLL.removeTop();
        return toRemove;
    }

    /** Returns number of items on stack with interface and method signature */
    public int size() {
        return mySLL.size();
    }
    /** Compute the sum of the numbers on the stack, non destructive per last test */
    public int sum() {
        int lenSLL = mySLL.size();
        int total = 0;
        for (int i = 0; i < lenSLL; i++) {   // careful: < not <=
            total += mySLL.getItem(i);
        }
        return total;
    }
}
