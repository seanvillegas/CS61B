#### Prof Hug Sp25
# Hw0a notes
#### Sean Villegas

[Guide](https://sp25.datastructur.es/homeworks/hw0/hw0a/)
## Data types

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


```java
String s = "hello";
s += " world";
s += 5;
int sLength = s.length();
String substr = s.substring(1, 5);
char c = s.charAt(2);
if (s.indexOf("hello") != -1) {
    System.out.println("\"hello\" in s");
}
for (int i = 0; i < s.length(); i++) {
    char letter = s.charAt(i);
    System.out.println(letter);
}

```

**What happens to each iteration of char i in memory?**
- `letter` is a local variable inside the loop.
- It's stored on the stack, not the heap.
- Each iteration reuses the same stack space, so you're not building up memory usage.
  - i.e., each iteration discards (or overwrites) the last value of the local variable, like `letter`.
  - You could store each variable with extra logic
- After the loop finishes, letter is gone completely

### Moral for iteraton in example
-  `Strings` are not directly iterable. We either iterate over an index and use `charAt`, or we convert it to an array
- you can append anything to a `String` variable with `+` or `+=`
  - and it will be implicitly converted to a String without needing to explicitly cast; because Java automatically converts non string values into strings before concatenation with `.toString()` method

### `void`
- void means the method does not return any value; it says what the method returns (in this case: nothing).