# Week 4
## Interface and Implementation Inheritance
## [Iterators Object Methods](https://www.youtube.com/watch?v=YpCZjgoBYJA&list=PLnp31xXvnfRrvh9PM0gK9fm7VAD0HdijJ&index=1)
## [Subtype Polymorphism, Function Passing, Generic Functions](https://www.youtube.com/watch?v=0qw-12Bp4Ew)
## 



```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name; // "this" is required, otherwise you'd just assign the parameter to itself
    }
}



public Person() {
    this("Unknown"); // calls the other constructor
}


public Person() {
    this("Unknown"); 
}


```


**Potential Watch Out:** common misconception (for confusing instanceof with .getClass())


### Implementing Comparable

```java
List<Dog> dogs = new ArrayList<>();
dogs.add(new Dog("Grigometh", 200));
dogs.add(new Dog("Pelusa", 5));
dogs.add(new Dog("Clifford", 9000));
Dog maxDog = Collections.max(dogs);


// ensure you have below
public interface Comparable<T> {
    int compareTo(T o);
}


public class Dog implements Comparable<Dog> {
   ...
   @Override
   public int compareTo(Dog uddaDog) {
       if (size > uddaDog.size) {
           return 1;
       }
       if (size < uddaDog.size) {
           return -1;
       }
       return 0;
   }
}

// less lines of code

public class Dog implements Comparable<Dog> {
   ...
   @Override
   public int compareTo(Dog uddaDog) {
       return this.size - uddaDog.size;
   }
}

```



### ArraySet
```java
import java.util.Iterator;
public class ArraySet<T> implements Iterable<T> {
    private T[] items;
    private int size; // the next item to be added will be at position size

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map.
       Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("can't add null");
        }
        if (contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    // most efficient way o(n)
    public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(items[i].toString());
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1]);
        returnSB.append("}");
        // use toString to have the correct type (to get the string outside of the string builder)
        return returnSB.toString();
    }


    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(items[i].toString());
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1]);
        returnSB.append("}");
        return returnSB;
    }

    @Override
    public String toStringOld() { 
        String returnString = "{";
        for (T item : this) {
            returnString += item; 
            returnString += ", ";
        }
        returnString += "}";
                // can be return returnString
        return returnString;
    }

    // quadratic time
    @Override
        public String toStringOld() { 
        String returnString = "{";
        for (int i = 0; i < size - 1; i += 1) {
            returnString += items[i].toStringOld();
            returnString += ", ";
        }
        returnString += "}";
        return returnString;
    }

    /* EXTRA VIDEO CODE
    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    } */

    /* EXTRA VIDEO CODE
    public static <Glerp> ArraySet<Glerp> of(Glerp... stuff) {
        ArraySet<Glerp> returnSet = new ArraySet<Glerp>();
        for (Glerp x : stuff) {
            returnSet.add(x);
        }
        return returnSet;
    } */


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArraySet<T> o = (ArraySet<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }

    // slides code
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (other instanceof ArraySet otherset) {
            if (this.size != other.size) { return false; }
            for (T x : this) {
                if (!otherSet.contains(x)) {
                    return false; 
                }
            }
            return true;
        }
        return false; 
    }

    public static void main(String[] args) {
        ArraySet<Integer> aset = new ArraySet<>();
        aset.add(5);
        aset.add(23);
        aset.add(42);

        //iteration
        for (int i : aset) {
            System.out.println(i);
        }

        //toString
        System.out.println(aset);

        //equals
        ArraySet<Integer> aset2 = new ArraySet<>();
        aset2.add(5);
        aset2.add(23);
        aset2.add(42);

        System.out.println(aset.equals(aset2));
        System.out.println(aset.equals(null));
        System.out.println(aset.equals("fish"));
        System.out.println(aset.equals(aset));

        //EXTRA VIDEO CODE
        //ArraySet<String> asetOfStrings = ArraySet.of("hi", "I'm", "here");
        //System.out.println(asetOfStrings);
    }

    /* Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */
}
```