public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. L is not allowed
     * to change. Must use recursion.
     *
     * This method is non-destructive, i.e. it must not modify the original list.
     */
    public static IntList incrRecursiveNondestructive(IntList L, int x) {
        if (L.rest == null) {
            L  = new IntList(L.first + x, null);
        } else {
            L = new IntList(L.first + x, incrRecursiveNondestructive(L.rest, x));
        }
        return L;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L == null) {
            return null;
        }
        incrRecursiveDestructive(L.rest, x);
        L.first = L.first + x;
        return L;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. May not modify the original list.
     */
    public static IntList incrIterativeNondestructive(IntList L, int x) {
        IntList dummy = new IntList(0, null);
        IntList tail = dummy;
        IntList currNode = L;

        while (currNode != null) {
            tail.rest = new IntList(currNode.first + x, null);
            tail = tail.rest;
            currNode = currNode.rest;
        }
        return dummy.rest;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed
     * to use recursion. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrIterativeDestructive(IntList L, int x) {
        IntList copyNode = L;
        while (L != null) {
            L.first = L.first + x;
            L = L.rest;
        }
        return copyNode;
    }

    /**
     * Returns an IntList consisting of the elements of L1 followed by the
     * elements of L2.
     */
    public static IntList concatenate(IntList L1, IntList L2) {
        if (L1 == null) {
            return L2;
        }
        if (L2 == null) {
            return L1;
        }

        IntList coppyNode = L1;
        while (L1.rest != null) {
            L1 = L1.rest;
        }
        L1.rest = L2;
        return coppyNode;
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    public static void outPut(IntList debugList) {
        IntList pter = debugList;

        while (pter != null) {
            if (pter.rest != null) {
                System.out.print(pter.first + " --> ");
            } else {
                System.out.print(pter.first + " --> null");
            }
            pter = pter.rest;
        }
    }
}
