
##### Prof Hug, Sp25
# Lecture 1
#### Sean Villegas

[Answers](https://csdiy.wiki/en/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E7%AE%97%E6%B3%95/CS61B/)

#

```java
package lec1_intro1;

public class LargerDemo {
        /** Returns the larger of x and y. */
        public static int larger(int x, int y) {
                if (x > y) {
                        return x;
                }
                return y;
        }

        public static void main(String[] args) {
                System.out.println(larger(3, 10));
        }
} 

/*
1. Functions must be declared as part of a class in Java.
   A function that is part of a class is called a "method".
   So in Java, all functions are methods.
2. To define a function in Java, we use "public static".
   We will see alternate ways of defining functions later.
3. All parameters of a function must have a declared type,
   and the return value of the function must have a declared type.
   Functions in Java return only one value!
*/
```

## Java Syntax
- `public static int` is the normal way to define a function in java
- `System.out.print("Hello world!");` will print on the same line compared to `ln` 




