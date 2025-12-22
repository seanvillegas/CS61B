public class RedBlackTree<T extends Comparable<T>> {

    /**
     * Notes:
     *      Using colored nodes as our representation, the root node must be colored black
     *          In some cases, we must remember to flip the root’s color back to black in our representation.
     *      If a node has a red child, it must be on the left
     *      No node can have two red children
     *      No red node can have a red parent (every red node’s parent is black)
     *      In a balanced LLRB tree, every path to a null reference goes through the same number of black nodes
     *
     *      **Whenever we insert a node into a LLRB, we insert it as a red node.**
     *      **Null nodes (children or leaf nodes) are automatically considered black.**
     */
    /* Root of the tree that should always be black. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        boolean temp = node.isBlack;
        node.isBlack = !node.isBlack; // R = F
        // Children flip to the opposite of the original parent color, not the newly flipped parent color.
        // guarantees correctness no matter the initial colors of the nodes.
        node.right.isBlack = temp; // B = T
        node.left.isBlack = temp; // B = T
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode oldParent = node;
        RBTreeNode newRoot = oldParent.left;
        oldParent.left = newRoot.right;
        newRoot.right = oldParent;

        newRoot.isBlack = oldParent.isBlack;
        oldParent.isBlack = false; // need to explicitly set to false instead of inverse of !oldParent.isBlack
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode oldParentNode = node;
        RBTreeNode newRoot = oldParentNode.right;
        oldParentNode.right = newRoot.left;
        newRoot.left = oldParentNode;

        newRoot.isBlack = oldParentNode.isBlack;
        oldParentNode.isBlack = false; // need to explicitly set to false instead of inverse of !oldParent.isBlack
        return newRoot;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true; // handles propagation up tree
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode(false, item);
        }

        // BST insertion
        if (item.compareTo(node.item) < 0) {
            // insert left
            node.left = insertHelper(node.left, item);
        } else if (item.compareTo(node.item) > 0) {
            node.right = insertHelper(node.right, item);
        } // do nothing if equal

        // rotations for LLRB invariants
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        // two red nodes invariant (4 node)
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }
}
