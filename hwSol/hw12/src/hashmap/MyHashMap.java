package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Sean Villegas
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // total number of elements in hashmap bucket
    private int numberElements;
    // total number of buckets that hashmap has, start with prime number
    private int numberBuckets = 16;
    // I am going to keep initCapacity rewriteable instead of static for each instance. but the starting is 16
    private int initCap = 16;
    // I am going to keep loadfactor rewriteable instead of static for each instance. but the starting is 0.75
    private double loadF = 0.75;

    /** Constructors */
    public MyHashMap() {
        // NOTE: you can lazily create buckets when first accessed so you dont compute N items eagerly
        //  new Collection[]{createBucket()} will work but new Collection[initCap]{createBucket()} wont
        buckets = new Collection[initCap];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        this.numberBuckets = initialCapacity;
        buckets = new Collection[numberBuckets];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.numberBuckets = initialCapacity;
        this.loadF = loadFactor;

        buckets = new Collection[numberBuckets];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // The mechanism by which different implementations of the hash table
        // implement different buckets is through this factory method
        return new ArrayList<>(); // return any type of collection per spec
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

        if (!containsKey(key)) {
            if (checkLoad()) {
                resize();
            }
            int bucketIndex = hash(key);
            Node newNode = new Node(key, value);
            buckets[bucketIndex].add(newNode);
            numberElements++;
        } else {
            int bucketIndex = hash(key);
            // update the key value if it already exists
            // this will loop through twice which could be inefficient
            Collection<Node> nodes = buckets[bucketIndex];
            for (Node i : nodes) {
                if (i.key.equals(key)) {
                    i.value = value;
                }
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int bucketIndex = hash(key);
        Collection<Node> nodeIndex = buckets[bucketIndex];
        // we return null if bucket doesn't exist, or if value is actually null?
        if (nodeIndex == null) {
            return null;
        }
        V valueLookUp = null;


        for (Node i : nodeIndex) {
            if (i.key.equals(key)) {
                valueLookUp = i.value;
            }
        }
        return valueLookUp;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        // values can be null so you cant check if get returns null

        int bucketIndex = hash(key);
        Collection<Node> nodeIndex = buckets[bucketIndex];

        for (Node i : nodeIndex) {
            if (i.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return numberElements;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        MyHashMap freshStart = new MyHashMap(initCap);
        buckets = freshStart.buckets;
        numberElements = 0;
        numberBuckets = initCap;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Loadfactor Validity check
     *
     * Either compare elements divided by buckets with the load factor
     * Or compare elements with (buckets × load factor).
     */
    private boolean checkLoad() {
        // cast to double so it never rounds down
        // online it says loadF * initCap
        double totalElements = numberElements;
        double totalBuckets =  numberBuckets;
        return totalElements / totalBuckets >= loadF;
    }

    /**
     * Resize function
     */
    private void resize() {
        Collection<Node>[] oldBuckets = this.buckets;
        this.numberBuckets *= 2;
        MyHashMap newHM = new MyHashMap(numberBuckets);
        buckets = newHM.buckets;

        for (Collection<Node> pairs : oldBuckets) {
            for (Node pair : pairs) {
                int newBucketIndex = hash(pair.key);
                buckets[newBucketIndex].add(pair);
            }
        }
    }

    /**
     * Hashing function for putting into the hash table
     * Use math.floorMod and abs to not get negative numbers
     */
    private int hash(K key) {
        int hashCode = key.hashCode();
        // math.abs might be overkill, since M should never be negative (i.e. y)
        // floorMod never returns negative unless int y is negative
        return Math.abs(Math.floorMod(hashCode, numberBuckets));
    }
}
