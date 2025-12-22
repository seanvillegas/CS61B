## Proj 2 Notes


Whats wrong with this?

```java
public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;

    /** Constructor */
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        T nextFirst = items[4];
        T nextLast = items[8];
    }

```

1. Need to have nextFirst and nextLast as class variables private int
2. nextFirst and nextLast are storing null, not integer indices marking where next first/last element should go
3. OOB
4. Start them 1 greater than for `(index ± 1 + capacity) % capacity` == function wrapAround
5. accessing an element in an array, use the index of an array[3] for example. 
6. translate from my zero, to your zero (doing things behind scene that user cant see)


## Scratch Paper


### addFirst
```java
    public void addFirst(T x) {
        items[nextFirst] = x;
        nextFirst--;
    }
    public void addFirst(T x) {
        int oldFirst = 4;
        items[nextFirst] = x;
        nextFirst = oldFirst - 1;
    }
```

### addLast
```java
    public void addLast(T x) {
    // size doesnt ever get greater than the length when we hit just before OOB so that means this is wrong. 
        if (size > items.length && items[0] == null) {
            nextLast = 0;
        }
        items[nextLast] = x;
        nextLast++;
        size++;
    }
```


### Capacity
1. capacity is taken by items.length thus we dont need to keep it in the class to be accessible since the current instance will always correctly all items.length

```java
    public T[] copyToResize() {
    // Hug: using get method is useful for resize method?
    //T[] resizeOldArr = items;
    int preAllocate = items.length * 2;
    T[] newArray = (T[]) new Object[preAllocate];
    // will capacity be automatically updated? I think so
    for (int i = 0; i <= size; i++) {
        // can i use resizeOldArr
        T element = get(i);
        // why wont this work (at first you set element in items[]. Items expects an integer
        newArray[i] = element;
    }
    newArray = items;
    return newArray;
}

```

### get
1. you should ensure that OOB index returns null
2. same with the case if the pass by value by user is negative index
3. you should return the logical order that the user would see; imagine that they are interacting with a python list and the start of the list is different to what you see as the engineer
4. `int userOrder = (this.nextFirst + 1 + userInputIndex + arrayLen.length) % arrayLen.length`


### isEmpty
1. return the condition instead of explicitly saying if else logic
```java
public boolean isEmpty() {
    return size == 0; // will return true or false based on this condition check
}
```


### Iterator Overriding 
1. We expand on Deque61B
2. Our Deque61B cant iterate with enhanced for loop and we cant call assertThat.constainsExactly from the Truth library due to the shortcoming of no iteration
3. Fix declaration in Deque61B interface to be: `public interface Deque61B<T> extends Iterable<T> `
4. Use lecture notes and all you had to Override was the Iterator<T> iterator() method. In my implementation I was able to iterate with this method by just replacing `items[wizPos]` with `get(wizPos)`

### equals
1. When you run the code provided by the project, you will get an error that the memory objects are not the same even though the internal elements within that different array list in memory has same contents. Here is what I saw:
```
expected: ArrayDeque61B@77c2494c
but was : [front, middle, back]
Expected :ArrayDeque61B@77c2494c
Actual   :[front, middle, back]
<Click to see difference>
```
2. The issue again is in the Truth class that we must override in our ArrayDeque61B class. Here is the code that literally will check the current object and see if its memory address is equal to the passed in object:
```java
public boolean equals(Object obj) {
    return (this == obj);
} 
```
3. You need to implement contains. Use get method to grab the userOrder logic index and call equals to check that variable. 
   - contains is apart of ArrayList class and therefore your custom class must create your own. You can solve this with iterator + get or just for loop and get

### toString
1. As with toString, the default toString method for System.out.println will print the class and then @ memory address
2. Simply override toString method with lecture code
3. I used StringBuilder as apparently its most efficient. Make sure to use get and I only call toString() when I return the newly created String vairable. 