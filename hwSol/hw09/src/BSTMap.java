import java.util.Iterator;
import java.util.Set;

/**
 * Reject null keys inputted because you cant determine if null is greater than x in put and get methods
 * Bounded type parameter allows you to call compareTo without much thought.
 * Clothe naked ds BSTNode
 * DS is Ω(log n) in best case if balanced tree (bushy)
 * DS is O(N) in worst case if input is in sorted order
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // BSTMap has a root field pointing to the top BSTNode
    private BSTNode root;
    private int size;

    /**
     * Nodes represent one key/value pair
     * @param <K>
     */
    private class BSTNode {
        private K item;
        private V valueByKey;
        private BSTNode left;
        private BSTNode right;

        public BSTNode(K item, V valueByKey, BSTNode left, BSTNode right) {
            this.item = item;
            this.valueByKey = valueByKey;
            this.left = left;
            this.right = right;
        }
    }

    // constructor
    BSTMap() {
        // initially, root is null. First put creates the root node
        root = null;
        size = 0;
        //BSTNode<K> node = new BSTNode<>(null,null, null);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Do not enter null as key");
        }
        // you must capture the returned node or else it will stay null and get fails
        root = walkDownTree(root, key, value);
        size++;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        // `containsKey` should only care about the keys, not the values!
        //return get(key) != null;
        return getKey(root, key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size -= size;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    // helper function
    public void printInOrder() {
        /**
         * Do not try to do a linked list approach of printing
         * for any node, check if null. If not:
         *      first recurse left, then print that item, then recurse right and print its item.
         */
        System.out.println(root.item);
        printKey(root);
    }

    public void printKey(BSTNode parentNode) {
        if (parentNode.left != null) {
            System.out.println(parentNode.left.item);
            printKey(parentNode.left);
        }

        if (parentNode.right != null) {
            System.out.println(parentNode.right.item);
            printKey(parentNode.right);
        }
    }

    /**
     * This helper returns BSTNode and lets you walk down each node recursively
     * Is destructive
     */
    private BSTNode walkDownTree(BSTNode parentNode, K k, V v) {

        if (parentNode == null) {
            return new BSTNode(k, v, null, null);
        }
        // if passed in key exists re-assign its value
        // Use .equals() for objects, not ==.
        // parentNode.item.equals(k) ensures you’re comparing key values, not references.
        if (parentNode.item.equals(k)) {
            parentNode.valueByKey = v;
            // delete size so its incremented appropriately outside in put method
            size--;
            // return the parentNode instead of overwriting it with a new child.
            return parentNode;
        }
        // IMPORTANT: you want to update that child reference (left or right subtree) and then
        // return the current parentnot the child.
        if (inOrderKeys(parentNode.item, k)) {
            parentNode.right = walkDownTree(parentNode.right, k, v);
            return parentNode;
        } else {
            parentNode.left = walkDownTree(parentNode.left, k, v);
            return parentNode;
        }
    }

    /**
     * Recursively walk down the BST and return the value if there, if not there, return null.
     */
    private V getHelper(BSTNode parentNode, K key) {
        if (parentNode == null) {
            return null;
        }

        if (parentNode.item.equals(key)) {
            return parentNode.valueByKey;
        }

        if (inOrderKeys(parentNode.item, key)) {
            return getHelper(parentNode.right, key);
        } else if (!inOrderKeys(parentNode.item, key)) {
            return getHelper(parentNode.left, key);
        } else {
            return null;
        }
    }

    /**
     * Since containsKey only cares about key, walk down the BST and find its corresponding key.
     * Trying to use get creates a bug. Because values can be null. keys CANT be null.
     */
    private K getKey(BSTNode parentNode, K key) {
        if (parentNode == null) {
            return null;
        }

        if (parentNode.item.equals(key)) {
            return root.item;
        }

        if (inOrderKeys(parentNode.item, key)) {
            return getKey(parentNode.right, key);
        } else if (!inOrderKeys(parentNode.item, key)) {
            return getKey(parentNode.left, key);
        } else {
            return null;
        }
    }

    /**
     * returns true if key1 is less than key2
     * write a helper method so you dont have to think about compareTo
     */
    private boolean inOrderKeys(K key1, K key2) {
        return key1.compareTo(key2) < 0;
    }
}
