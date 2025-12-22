#### Prof Hug Sp25 
# CS61B HW0B Notes
#### Sean Villegas

#

## Two types:
- Primitive types
    -  Primitive types are lowercase e.g  `boolean`, `int`, `char`, `double`
- Reference types
    - Everything else is a reference type. If type starts with capital letter, odds are reference e.g. `String`

## Each Primitive has a corresponding reference type
- If you are using generics to declare a data structure, you must use the reference type
- i.e. we set the type to a constraint 

## null
- any reference type can be assigned a value of null 


## member variables / instance variables
- length in arrays is a field, not a method — so you use `array.length`, not `array.length()`

## `Arrays` 
- do not print nicely 
- Arrays don’t print nicely because calling `System.out.println(array)` shows the **memory reference/machine code** not the contents.
- no negative indexing or slicing  

## `for` and `:`
- `for (Type var : collection)` reads as: **for each var in collection**
    ```java
    int[] array = {1, 2, 3};
    for (int i : array) {
        System.out.println(i);
    }
    ```
- works on Lists, Sets

## `Lists`
- no negative indexing or slicing  
- parameterized by type it holds with `<>`
    ```java
    List<String> lst = new ArrayList<>();
    lst.add("zero");
    lst.add("one");
    lst.set(0, "zed");
    System.out.println(lst.get(0));
    System.out.println(lst.size());
    if (lst.contains("one")) {
        System.out.println("one in lst");
    }
    for (String elem : lst) {
        System.out.println(elem);
    }
    ```

## Sets
```java
Set<Integer> set = new HashSet<>();
set.add(1);
set.add(1);
set.add(2);
set.remove(2);
System.out.println(set.size());
if (set.contains(1)) {
    System.out.println("1 in set");
}
for (int elem : set) {
    System.out.println(elem);
}
```
**Implementations**:
- TreeSet
    - elements in sorted order ans is fast
- HashSet
    - no defined order and computationally fast
- Sets **have no duplicate items**. Adding a duplicate item will do nothing

## Maps
```java
Map<String, String> map = new HashMap<>();
map.put("hello", "hi");
map.put("hello", "goodbye");
System.out.println(map.get("hello"));
System.out.println(map.size());
if (map.containsKey("hello")) {
    System.out.println("\"hello\" in map");
}
for (String key : map.keySet()) {
    System.out.println(key);
}
```
- Same logic in terms of computation for `TreeMap` and `HashMap`
- not contain duplicate keys
    - if you add duplicate the value is overwritten.
- `keySet` for iterating over set of keys and returning the key
- `entrySet` for iterating over each element of key and values, whilst returning both values

## Classes Example

```java
public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point() {
        this(0, 0);
    }
    public double distanceTo(Point other) {
        return Math.sqrt(
            Math.pow(this.x - other.x, 2) +
            Math.pow(this.y - other.y, 2)
        )
    }
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public static void main(String[] args) {
        Point p1 = new Point(5, 9);
        Point p2 = new Point(-3, 3);
        System.out.println("Point 1: ( " + p1.x
            + ", " + p1.y + ")");
        System.out.println("Distance: "
            + p1.distanceTo(p2));
        p1.translate(2, 2);
        System.out.println("Point 1: ( " + p1.x
            + ", " + p1.y + ")");
    }

}
```

## Useful code for HW
```java
public class MinIndexExample {

    // Method to find the index of the minimum value in an array
    public static int minIndex(int[] numbers) throws Exception {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("There are no elements in the array!");
        }

        int m = numbers[0]; // Assume the first element is the minimum
        int idx = 0; // The index of the minimum value

        for (int i = 1; i < numbers.length; i++) { // Start from the second element
            if (numbers[i] < m) {
                m = numbers[i];
                idx = i;
            }
        }
        return idx;
    }

    public static void main(String[] args) {
        try {
            int[] numbers = {5, 2, 9, 1, 7};

            // Call minIndex to get the index of the minimum element
            int index = minIndex(numbers);
            System.out.println("The index of the minimum value is: " + index);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
```


## Solving problems, Where you went Wrong is important to understand 

### Problem 2: 

```java
    /**
     * Returns the order depending on the customer.
     * If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     * If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     * In any other case, return an empty String[] of size 3.
     */
    public static String[] takeOrder(String customer) {
        Map<String, String> stringMap = new HashMap<>();
        String[] ergunArr = {"beyti", "pizza", "hamburger", "tea"};
        String[] erikArr = {"sushi", "pasta", "avocado", "coffee"};
        stringMap.put("Ergun", Arrays.toString(ergunArr));
        stringMap.put("Erik", Arrays.toString(erikArr));
        String[] error = "   "; // "   " wont give size of 3, 
        boolean equals = customer.equals(stringMap.containsKey(customer));
        if (equals) {
            return new String[]{stringMap.get(customer)};
        } else {
            return error;
        }
    }

```
- `String[] error = "   ";` will give a size of 1, not 3. Always check the size
- storing all the elements in an array for the single get call is not the expected output of `" "` around each item
- the `equals` call is comparing a `String` to a `boolean`, and it will always return false
- `List.of` will return a constrained list
- `Array.list` will return a resizable list 

# ListExercises 

