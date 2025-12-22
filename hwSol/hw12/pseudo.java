class HashPseudo {

    /*Instance variables */
    Collection<Node>[] buckets; // hashmap
    numberOfElements 
    numberOfBuckets // number of buckets
    loadFactor = 0.75
    final initialCapacity = 16

    // constructor 
    HashPseudo() 


    private put(key, value) {
        if (key == null) { wont ever happen in this project }  

        if (!buckets.containsKey()) {
            if ( checkLoad() ) {
            // resize the hashtable with multiplication, not additively
                resize()
            }
            bucketIndex = hash(key)
            Node nodeToInsert = Node(key, value)
            buckets[bucketIndex].add(nodeToInsert)
            numberOfElements++
        } else {
            bucketIndex = hash(key)
            Collection<Node> nodes = buckets[bucketIndex]; 
            for (Node node in nodes) {
                if (node.key.equals(key)) {
                    node.value = value;
                }
            }
        }
    }

    // collisions with separate chaining
    hash(Key) {
        int hashCode = key.hashCode();
        return Math.abs(Math.floorMod(hashCode, numberOfBuckets))
    }

/* BUGGY CODE */
    private void resize() {
        // Collection<Node>[] needs to be cast
        // its not liking the casting, maybe we must cast it in the for loop.
        //Node[] oldBuckets = (Node[]) (this.buckets);
        Collection<Node>[] oldBuckets = this.buckets;
        this.numberBuckets *= 2;

        //  this could work
        MyHashMap newHashMap = new MyHashMap(numberBuckets);
        buckets = newHashMap.buckets;

        // i feel like this reads better
        //buckets = new Collection[numberBuckets];

        for (Collection<Node> pair : oldBuckets) {
            Node item = (Node) (pair);
            int newBucketIndex = hash(item.key);
            buckets[newBucketIndex].add(item);
        }
    }

                    /**
                     * R: You're iterating buckets wrong. Each bucket is a Collection<Node>,
                     * but you're treating the Collection itself as a Node.
                     * That’s the ClassCastException.
                     * Solution:
                     */

                    private void resize() {
                        Collection<Node>[] oldBuckets = this.buckets;
                        this.numberBuckets *= 2;
                        buckets = new Collection[numberBuckets];

                        for (Collection<Node> pairs : oldBuckets) {

                            for (Node pair : pairs) {
                                int newBucketIndex = hash(pair.key);
                                buckets[newBucketIndex].add(pair);
                            }
                        }
                    }


    Value get(key) {
        bucketIndex = hash(key)
        Collection<Node> bucketOfInterest = buckets[bucketIndex]

        for (Node pair : bucketOfInterest) {
            if (pair.key.equals(key)) {
                return pair.value
            }
        }
        return null;
    }
    boolean containsKey(key) {
        // we dont rely on get on this implementation because it can return null and null is allowed
        bucketIndex = hash(key)
        Collection<Node> bucketOfInterest = buckets[bucketIndex]

        for (Node pair : bucketOfInterest) {
            if (pair.key.equals(key)) {
                return true
            }
        }
        return false
    }


    private class Node {
        Key
        Value

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
}