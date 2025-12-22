#### Prof Hug
# CS61B Cheat Sheet
#### Sean Villegas


## Docs/Libraries used in course 
- [Oracle](https://docs.oracle.com/en/java/)
- [Princeton](https://introcs.cs.princeton.edu/java/stdlib/)
- [Style](https://sp25.datastructur.es/resources/guides/style/)

**_This is a guide based on all the problems I have solved in CS61B:_**
- The most important idea to take away from solving problems is not memorizing but how you solve it and translate the idea into code through repetition 

## Basics 

<details>

- `boolean`
- `int`
  - Python integers can be as big as you want, but Java integers have fixed size limits.
  - i.e. In Java, an int has a fixed size of 32 bits, so it can only hold values between -2,147,483,648 and 2,147,483,647. If you try to go beyond that, it will overflow (wrap around and give the wrong number).
- `float`
  ```java
    float price = 3.14f;  //  correct
    float wrong = 3.14;   //  error! 3.14 is treated as a double
  ```
  - Use **float** when memory is limited and you don't need high precision.
  - float has size 32 bits, ~7 decimal digits
  - default type, requires f 
- `double` 
  - for better accuracy and when precision matters, default choice for decimal numbers in java
  - size 64 bits; ~15-16 decimal digits
- `String`
  - uses double quotes and can be any text
- `char` 
  - is useful in low level programming, memory efficient. 
  - `char` variables are inside single quotes; can be ASCII values or Unicode escape sequences
    ```java
      char myGrade = 'B';
      System.out.println(myGrade); // Output: B
    
      char myVar1 = 65, myVar2 = 66, myVar3 = 67;
      System.out.println(myVar1); // Output: A
      System.out.println(myVar2); // Output: B
      System.out.println(myVar3); // Output: C
    
      char unicodeChar = '\u03A9'; // Unicode for uppercase Greek omega
      System.out.println(unicodeChar); // Output: Ω
      ```

## `while`
```java
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;
}

initialization
while (termination) {
    // loop body
    increment
}
```
- parentheses around condition required 
- curly braces around logic; note that Java doesn't require indenting

## `for`
```java
for (int i = 0; i < 10; i ++) {
    System.out.println(i);
}

for (initialization; termination; increment) {
    // loop body
}

public class MainClass {
}
    /*
            9
            8
            7
            ...
     */
    public static void main(String[] args) {
        for (int i = 9; i >= 0; i --) {
            System.out.println(i);
        }

    }
```

- `--` means decrement by 1
- `++` increment by 1

## Conditionals 
```java
if (i % 3 == 0 && i % 5 == 0) {
    System.out.println("FizzBuzz");
} else if (i % 3 == 0) {
    System.out.println("Fizz");
} else if (i % 5 == 0) {
    System.out.println("Buzz");
} else {
    System.out.println(i);
}
```

## Boolean Operators
- `&&` // and
- `||` // or
- `!` // not
- `==` // is equal

## `==` and `.equals()` in java 
- `==` checks if two references point to the same object in memory (identity).
- `.equals()` checks if two objects have the same value/content (equality).
    ```java
    String a = new String("hello");
    String b = new String("hello");
    
    a == b        // false (different objects)
    a.equals(b)   // true (same content)
    ```

## Exponentiation 
```java
double x = Math.pow(2, 10); // 1024
```

## `XOR` operator
- compares each bit (binary digit) of the two numbers individually based on truthy table and creates a new binary representation, thus outputting the new number
```java
// 2 ^ 10 will output 8 
/*
   0010   (2 in binary)
^  1010   (10 in binary)
=  1000   (8 in binary)
*/
```
- XOR returns 1, true if one of the inputs is 1
- XOR returns 0, false if both inputs are the same (both are 0 or both are 1)

| A | B | A XOR B (`A ^ B`) 
|---|---|-------------------| 
| 0 | 0 | 0                 |
| 0 | 1 | 1                 |
| 1 | 0 | 1                 |
| 1 | 1 | 0                 | 

## Functions
```java
public static String greet(String name) {
    return "Hello, " + name;
}
// Elsewhere...
System.out.println(greet("Josh"));
```
- `public` means it is accessible everywhere (i.e. can be used in other classes)
  - other classes can call it or use it directly without any restrictions.
- `static` means it belongs to the class, not to instances 
  - This means the method or variable belongs to the class itself, rather than to instances (objects) of the class. 
  - You can call a static method or access a static variable without creating an object of the class.
- java code runs inside `public static void main(...)` compared to python, where the code can run anywhere in the script

### Built in functions
- `.substring(int beginIndex, [int endIndex])`
  - It is important to note that `substring()` creates a **new string object**, and the original string remains unchanged. If the provided indices are out of bounds, a `StringIndexOutOfBoundsException` is thrown.

- `.indexOf(String str)`
  - Searches for the first occurrence of the string `str` in the string, saving the index value.
  - `.indexOf(String str, int fromIndex)` 
    - searches for first occurrence of string `str`, starting from search at index `fromIndex`
  - `indexOf(int ch)`
    - Searches for the first occurrence of the character `ch` (represented as its Unicode value).
  - `indexOf(int ch, int fromIndex)`
    - Searches for the first occurrence of the character `ch`, starting the search at the index `fromIndex`
- [Math library](https://www.w3schools.com/java/ref_math_max.asp)
- [.indexOf](https://www.w3schools.com/java/ref_string_indexof.asp)
</details>


## Math
```java
+ // addition
- // subtract
* // multiplication
/ // division
% // Mod remainder of division 
```

## Bitwise
```java
& // Bitwise AND
| // Bitwise OR
^ // Bitwise XOR
~ // Bitwise NOT
<< // Left shift
>> // Right shift
>> // Unsigned right shift
```

## Assignment Operators
```java
= // Simple assignment
+= // Add and assign
-= // Subtract and assign
*= // Multiply and assign
/= // Divide and assign
%= //Modulo and assign
```

## Comparison Operators:
Used to compare values.
==: Equal to
!=: Not equal to
>: Greater than
<: Less than
>=: Greater than or equal to
<=: Less than or equal to
#
## `.StringBuilder`
- `String` in java is immutable, thats why we use **StringBuilder** which allows in-place modifications by creating a new object, making it memory-efficient and faster for frequent string operations.
- `.StringBuffer` does same thing, but it guarantees synchronization 
    - *Why?* to do with **threading**
```java
StringBuilder sb = new StringBuilder("Initial String");
```

## `.reverse()`
- Built in method of `StringBuilder` class that reverses sequence of characters in `StringBuilder` object
```java
StringBuilder sb = new StringBuilder("hello");
sb.reverse();  // sb now contains "olleh"
System.out.println(sb);
```

## `for` loop for iterating over a string with `charAt()`
- `charAt()` allows you to save the current variable in the local frame at each iteration 

## `String.valueOf()`
- `valueOf()` method is apart of the `String` class in Java, used to convert any non-String variable or Object such as int, double, char, and others to a newly created String object


## Concatenating two `char` data types result 
- concatenating two `char` variables will result in an `int` data type rather than a `String`. 
    - `char` uses numeric values (i.e. unicode) to represent their code points. Performing any arithmetic on it will produce a numeric result
- To add the `string` data type, call `String.valueOf()` method or `StringBuilder` class


# Strings
- `+=` is inefficient when working with String data types.
    - This is because strings in Java are immutable, meaning that each concatenation creates a new `String` object. 
- Thus, use `StringBuilder` or `StringBuffer`

## `StringBuilder`
- returns reference to StringBuilder object itself, allowing for method chaining
- `toString()` returns String representation of `StringBuilders` content
- builds strings efficiently by modifying a mutable character sequence.


# Boolean Operators 

## `&&`
- Short circuit behavior, analyze second expression iff first expression evaluates to true 
- Both booleans must be truthy; else is falsy
- It is used to combined two bolean expressions. 
    ```java
    boolean a = true;
    boolean b = false;
    System.out.println(a && b); // Output: false
    System.out.println(a && !b); // Output: true
    ```

## `&` 
- Used to compare boolean values of Binary representations of integers or boolean values. E.g.
    ```java
    int x = 5; // Binary: 0101
    int y = 3; // Binary: 0011
    System.out.println(x & y); // Output: 1 (Binary: 0001)

    boolean p = true;
    boolean q = false;
    System.out.println(p & q); // Output: false
    ```

# If
- If statements by themselves, always require some value to `return`. Java compiler _knows_ that we have conditions to check, but if no condition is met, you must have a return statement according to logic. 
    - in general, follow `if` and `else if` logic. Recall that `else` returns a value and has no boolean check
    ```java
    // shorthand if-else
    condition ? value_if_true : value_if_false
    ```


# Summing 

## `Integer.sum()` and `Double.sum()`
- part of the `Integer` class
- will sum two numbers together 

## `Arrays.stream(numbers).sum();`
- import java.util.Arrays; 
```java
int[] numbers = {1, 2, 3, 4, 5};
int sum = java.util.Arrays.stream(numbers).sum(); // Returns 15
```

## For 

# Streams
_many operations of stream data-types_
- For summing elements in a collection (like an array or a list)

## Stream `mapToInt(ToIntFunction mapper)`
- returns an IntStream consisting of the results of applying the given function to the elements of this stream.
- an intermediate operation. **These operations are always lazy**
- Intermediate operations are invoked on a Stream instance and after they finish their processing, they give a Stream instance as output.
```java
IntStream mapToInt(ToIntFunction<? super T> mapper)

/*
    Where, IntStream is a sequence of primitive 
    int-valued elements and T is the type 
    of stream elements. mapper is a stateless function 
    which is applied to each element and the function
    returns the new stream.
*/ 

```

## Lambdas `->` 
```java
(parameters) -> expression
// or
(parameters) -> { statements; }
```
### Functional interfaces
```java
// Predicate
Predicate<Integer> isEven = number -> number % 2 == 0;
System.out.println(isEven.test(4)); // true

// Consumer
Consumer<String> printMessage = message -> System.out.println(message);
printMessage.accept("Hello, Lambda!");

// Function
Function<String, Integer> stringLength = str -> str.length();
System.out.println(stringLength.apply("Java")); // 4

// Supplier
Supplier<Double> randomValue = () -> Math.random();
System.out.println(randomValue.get());
```

### `::`
- method reference operator. 
    - method reference like `Integer::parseInt` is shorthand for a lambda `x -> Integer.parseInt(x)`
    - It works only with functional interfaces (interfaces with one abstract method)
    - The method being referenced must match the interface’s method signature (input/output types).
- It provides a shorthand notation for lambda expressions that call existing method
    - Enhances code readability by directly referencing methods instead of writing out the full lambda syntax.
- 4 types of method references: 
    - Static: `ClassName::staticMethodName`
    - Instance m ethod of particular object: `object::instanceMethodName`
    - Instance method of arbitrary object of a particular type: `ClassName::instanceMethodName`
    - Constructor: `ClassName::new`




# Built in Methods
- [`java.util.ArrayList`](https://www.w3schools.com/java/java_arraylist.asp)
    - Specifying the type, String, Integer, etc creates an object wrapper for ArrayList, HashSet, to work with the data since Java functionality can only have streams of objects rather than primitive data 
- First import, then specify the type with angle symbol
- `<name>.addAll(Arrays.asList(type, type, type));`
    - will add a whole list as a finite list, and `list.addAll` converts back to resizable 
```java
import java.util.ArrayList; // import the ArrayList class

ArrayList<String> cars = new ArrayList<String>(); // Create an ArrayList object
cars.add("apple"); // manually add 
```
- On hw0b I learned that when we specify what we need to return, it means that we must return that data type. 

## `Maps`
- [Docs](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)
- use `.get` to get values in a map 
- [GFG Map Interface](https://www.geeksforgeeks.org/map-interface-java-examples/)

```java
import java.util.Map;
import java.util.TreeMap;

public class Maps {
    public static void main (String[] args) {
        Map <dataType, dataType> Demo = new typeMap<>();
        Demo.put(”key”, ”value”);
        Demo.put(”key1”, ”value”);
        String output = Demo.get(”key1”);  
        System.out.println(output);
    }
}

```

## `java.util.List`
[Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html)
[GFG]()
- you can resize, it is an ordered (sequence) collection of elements
- can mutate based off index 
- needs ArrayList
- `addAll()` allows you to add multiple elements in one line to make code more readable 
- `list.of()` creates immutable list 




```java
import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public static void main(String[] args) {
        // Creating an ArrayList of Strings
        List<String> stringList = new ArrayList<>();

        // Creating an ArrayList of Integers
        List<Integer> integerList = new ArrayList<>();
    }
}
```

## `equals()` and `==` code
- `equals()` compares instance values
    - always for strings to compare content, *do they contain the same content* 
- `==` Checks if two objects are actually the same object in memory.
    - performs a comparison between its operands and returns a boolean value. 
        ```java
        int x = 5;
        int y = 5;
        System.out.println(x == y); // returns True 
        ```
    - with strings, **ask are you like me** instead of **are you you?**


## Polymorphism 
**Signficado**: 

you can return any object that implements the interface you're declaring as the return type.

- e.g. `ArrayList<Integer>` is a subtype of `List<Integer>`
- So anywhere a `List<Integer>` is expected (like a return type), it's totally valid to return an `ArrayList<Integer>`
    - You promise to return a Vehicle `(List<Integer>)`
    - And you return a Car `(ArrayList<Integer>)`


## `java.util.OptionalInt`
[Docs](https://docs.oracle.com/javase/8/docs/api/java/util/OptionalInt.html)
- A container object which may or may not contain a int value. If a value is present, `isPresent()` will return true and `getAsInt()` will return the value.
- You can use this problem in conjugation with Arrays.stream 
- `getAsInt()` will return it back to its primitive data-type



# Converting Objects to Primitives

### `java.util.List`
- `.get()` will autounboxing its primitive value


### Array List to String Array 
https://www.geeksforgeeks.org/convert-an-arraylist-of-string-to-a-string-array-in-java/

- `for (int i : List)` will autounbox the element


### Sorting list
- https://www.w3schools.com/java/java_sort_list.asp




# GFG
- [Convert a String to Character Array in Java
](https://www.geeksforgeeks.org/convert-a-string-to-character-array-in-java/)
- [Java StringBuilder Class
](https://www.geeksforgeeks.org/stringbuilder-class-in-java-with-examples/)

# TO DO
- create a char guide