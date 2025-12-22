## CREATE CIRCULAR SENTINEL   
    /** Create empty LinkedListDeque61B. This is the constructor */
    public LinkedListDeque61B() {
        sentinel = new Node(null, null);
        // try 1 
        //sentinel.next = sentinel.prev;
        //sentinel.prev = sentinel.next;
        

        // try 2
        //sentinel.next = new Node(null, null);
        //sentinel.next.next = sentinel.next.prev;
        //sentinel.next.prev = sentinel.next.next;
        size = 0;
    }

## ADD FIRST

// first attempt 
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next);
        //sentinel.next.next = sentinel.prev;
        // I lose the last addFirst item here.
        sentinel.next.prev = sentinel.next;
        sentinel.next.prev = sentinel.prev;
        //sentinel.next.prev = sentinel.prev;
        //sentinel.next.prev = sentinel;
        size++;
    }


   
// second attempt 
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next);
        // the below just rewrites it, I think this is wrong
        sentinel.next.prev = sentinel.next;
        sentinel.next.prev = sentinel.prev;
        //sentinel.next.prev = sentinel.prev;
        //sentinel.next.prev = sentinel;
        size++;
    }


// third attempt 
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next);
        sentinel.next.prev = sentinel.next;
        size++;
    }

// fourth attempt
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next);
        sentinel.next.prev = sentinel.prev;
        size++;
    }

// solution
@Override
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel.next);
        sentinel.next.prev = sentinel.next;
        size++;
    }


"""
Thoughts:

1. The last next item points back to the sentinel indicating we reach the end of the list; thats correct
2. I need to figure out how to write the new addFirst node .prev to point back at its predecessor, next
    - as of right now, it points at its start of the list
3. I believe my fourth attempt is correct. Java visualzer makes it look like its pointer back at the original sentinel. But what makes me doubt myself is that the pointer should be pointing at the PREDECESSOR node, not pointing at the beginning of the list again. 



Solution: 

To implement the data structure for addFirst:

    1. create the new node with sentinel.next = new Node(x, sentinel.next)
        - this creates a new instance of Node in sentinel.next, whilst also preservering old sentinel.next pointer passed into the newNode (pointing back to sentinel due to end of list)
    2. Then, update sentinel.next.prev = sentinel.next
        - this takes the oldNodes prev and points it to the the predecessor (the new node)
"""



### ADDLAST

// first attempt
    @Override
    public void addLast(T x) {
        // ruins the pointer to point to last of list
        sentinel.prev = new Node(x, sentinel.prev);
        // adjust the last node.prev to point to predecessor node
        sentinel.prev.prev = sentinel.next;
        sentinel.next = sentinel.next.next;
        // rewrite prev to be to sentinel after destruction
        sentinel.prev = sentinel;
        //sentinel.prev.prev = sentinel.next.next;
        //sentinel.next.next = sentinel.prev;
        //sentinel.next.next.prev = sentinel.next;
        size++;
    }

// second attempt

    @Override
    public void addLast(T x) {
        // ruins the pointer to point to last of list // the pointer prev should be the last item preserved
        sentinel.prev = new Node(x, sentinel);
        // set the lastNode.prev to be previous oldNode
        sentinel.prev.prev = sentinel.next;
        // set the oldNodes.next to be the lastNode object
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

        // this try gave nullpointerexception
        sentinel.prev.prev = sentinel.prev.prev.prev;

    public void addLast(T x) {
        // sentinel.prev will start us at the end of the list. Now the dummy sentinel will have a pointer to last item
        sentinel.prev = new Node(x, sentinel);
        // set the lastNode.prev to be previous oldNode
        sentinel.prev.prev = sentinel.next;
        // set the oldNodes.next to be the lastNode object
        sentinel.prev.prev.prev.next = sentinel.prev;
        size++;
    }

        public void addLast(T x) {
        // sentinel.prev will start us at the end of the list. Now the dummy sentinel will have a pointer to last item
        sentinel.prev = new Node(x, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
        sentinel.prev.prev = sentinel.prev.prev.prev;
        size++;
    }
"""
1. We should actually make sure that the dummy sentinel node .prev should point to the LAST node in the list. So I had a misunderstanding there

"""



### getRecursive

// 1st attempt
    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        Node currentNode = sentinel.next;
        if (index == 0) {
            return currentNode.item;
        } else {
            currentNode = currentNode.next;
            return getRecursive(index - 1);
        }
    }
}

"""
1. This code will correctly traverse but in each frames memory it resets to sentinel.next, therefore returning the item at the first node. 
2. Can this be implemented in a single method? 
3. 
"""


### get 

    public T get(int index) {
        if (index >= size() && isEmpty()) {
            return null;
        }

        Node pointer = sentinel.next;
        T element = null;

        // while (pointer != sentinel) would create infinite loop

        for (int idx = 0; idx < index; idx++) {
            while (pointer.next != sentinel) {
                element = pointer.item;
                pointer = pointer.next;
            }
        }
        return element;



### size bug


    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        // code wouldnt compile because size wasnt made static?
        //int sizeMe = LinkedListDeque61B.size();
        //int sizeMe = sentinel.size();
        // infinite loop
        //int sizeMe = this.size();
        //int sizeMe = LinkedListDeque61B.size; //setting size to private static int size means
        // that if you create another deque it will share the same size, no bueno
        // return sizeMe;
        return this.size;
    }