#### Prof Hug Sp25
# CS61B Ch2 Notes
#### Sean Villegas

#

## Static vs. Non Static Methods 

### Static Methods

```java
public class Dog { // Dog class doesnt do anything, we've defined something a dog CAN do. To run the class we can make a main method inside Dog or create a client of Dog 
    public static void makeNoise() {
        System.out.println("Bark!");
    }
}

public class DogLauncher {
    public static void main(String[] args) {
        Dog.makeNoise();
    }
}
```

- The above code shows us how to make a client, i.e. `DogLauncher`
    - A class that uses another class is sometimes called a "client" of that class
- Making a main method inside `Dog`, or making a client class that uses `Dog` are not options better/worse. 
    - the logic behind choosing a main method and client approach depends on the idea
#

### Instance Variables and Object Instantiation

- instances hold data, and when they are instantiated they are allocated memory to hold said data
```java
public class Dog {
    public int weightInPounds; // class variable or instance variable throughout textbook

    public void makeNoise() {
        if (weightInPounds < 10) {
            System.out.println("yipyipyip!");
        } else if (weightInPounds < 30) {
            System.out.println("bark. bark.");
        } else {
            System.out.println("woof!");
        }
    }    
}

public class DogLauncher {
    public static void main(String[] args) {
        Dog d;
        d = new Dog(); // new instance (object) of Dog class in memory 
        d.weightInPounds = 20;
        d.makeNoise();
    }
}
```
- `new` is your ticket to bringing blueprints (classes) to life as real objects.
- The Dog class has its own variables, also known as instance variables or non-static variables.
    - **These must be declared inside the class**
        - unlike languages like Python or Matlab, where new variables can be added at runtime.
- The method that we created in the Dog class did not have the `static` keyword. We call such methods **instance methods or non-static methods.**
- Declaring a variable sets the stage; creating an object brings the actor on. 
    ```java
    Dog d;
    d = new Dog() 
    // cleaner
    Dog d = new Dog(); 
    ```

    ```mermaid
    flowchart TD
        subgraph Stack_Memory
            A["Reference Variable
            Dog d;"]
        end

        subgraph Heap_Memory
            B["Dog Object
            new Dog();"]
        end

        A --> B
    ```
- whenever we create an object a space is created in heap
- Variables and methods of a class are also called members of a class.
- Members of a class are accessed using dot notation.

### Constructors 
e.g.
```java
public class DogLauncher {
    public static void main(String[] args) {
        Dog d = new Dog(20); // parameterized to save time
        d.makeNoise();
    }
}
```
- for parameterized syntax we employ the **constructor**
```java
public class Dog {
    public int weightInPounds;

    public Dog(int w) { // constructor, similar to __init__ in python
        weightInPounds = w;
    }
}
```

## Terminology 

#

### Array Instantiation
- arrays are also instantiated in Java using the new keyword
```java
public class ArrayDemo {
    public static void main(String[] args) {
        /* Create an array of five integers. */
        int[] someArray = new int[5]; // i of 0-4 space; promise
        someArray[0] = 3; // assign a int to the index value of 0
        someArray[1] = 4;
    }
}
```

### Arrays of Objects 
- this is cool: we can create arrays of instantiated objects
```java
public class DogArrayDemo {
    public static void main(String[] args) {
        /* Create an array of two dogs. */
        Dog[] dogs = new Dog[2];
        dogs[0] = new Dog(8);
        dogs[1] = new Dog(20);

        /* Yipping will result, since dogs[0] has weight 8. */
        dogs[0].makeNoise();
    }
}
```
2 uses of new: 
- `new` creates a Dog class promise within the array `dogs`
- We init Dog objects with `new`; creating them

## Class vs. Instance
- Class methods are static methods
    - Static methods are actions that are taken by the class itself. e.g.
    ```java
    x = Math.sqrt(100); // works just fine
    ```
- Instance methods are non-static methods
    - Instance methods are actions that can be taken only by a **specific instance of a class.**
    - if the `sqrt` was an instance method, see how to use it:
    ```java
    Math m = new Math();
    x = m.sqrt(100);    
    ```
    - If instance method: 
        - instance methods require an object to act upon.
        - The object `m` would be the one performing the `sqrt` operation.

**Dog example**
- Static method for comparing dog sizes

    ```java
    public static Dog maxDog(Dog d1, Dog d2) {
    if (d1.weightInPounds > d2.weightInPounds) {
        return d1;
    }
    return d2;

    // invoked by 
    Dog d = new Dog(15);
    Dog d2 = new Dog(100);
    Dog.maxDog(d, d2); // We call Dog class here because static
    }

    ```
- Instance method for comparing dog sizes

    ```java
    public Dog maxDog(Dog d2) {
    if (this.weightInPounds > d2.weightInPounds) {
        return this; // used to refer to the current object you use with the dot method
    }
    return d2;

    // invoked below 
    Dog d = new Dog(15);
    Dog d2 = new Dog(100);
    d.maxDog(d2); // invoked with specific instance method name 
    }
    ```

- Exercise: 
    ```java
    public static Dog maxDog(Dog d1, Dog d2) {
    if (weightInPounds > d2.weightInPounds) { // weightInPounds needs to access method 
        return this; // bug you must return the first Dog that is bigger
    }
    return d2;
    }
    ```
- Static methods don’t have a “self” — don’t ask them who they are.
- Similarly, weightInPounds is an instance variable, and you cannot access instance variables directly from a static method without referring to a specific instance (e.g., d1.weightInPounds).


### A note on static variables and accessing them 
```java
public class Dog {
    public int weightInPounds;
    public static String binomen = "Canis familiaris";
    ...
}
```
- Static variables should be accessed using the **name** of the class rather than a specific instance, e.g. you should use `Dog.binomen`, not `d.binomen.`


**Exercise:**
```java
public class DogLoop {
    public static void main(String[] args) {
   		Dog smallDog = new Dog(5);
   		Dog mediumDog = new Dog(25);
   		Dog hugeDog = new Dog(150);
 
   		Dog[] manyDogs = new Dog[4];  // 0 1 2 3  ALL set to NULL 
        manyDogs[0] = smallDog; // assign at specific values 
   		manyDogs[1] = hugeDog;    	   
        manyDogs[2] = new Dog(130); 
        // the third index is null, in our while loop it will compare the whole length, thus raising java.lang.NullPointerException
   		int i = 0;
   		while (i < manyDogs.length) {
       		Dog.maxDog(manyDogs[i], mediumDog).makeNoise();
       		i = i + 1;
        }
    }
}
```
- Java arrays have fixed length, i.e. range of 4 for index up to 3
- The new array with 4 integers is an array in memory that has promise values (i.e. we assign them values later throughout the program).
- The 3rd value is null, The issue is not in length computation — it’s when you use the null value (manyDogs[3]) and try to call a method on it.



- `String[] args` represents an array of strings named args that is passed as an argument to the main method. 
    - The `main` method is the entry point of any Java program, and `String[] args` allows the program to receive command-line arguments when it is executed.
    - at every i in `args`, it will be a separate command line argument
    ```java
    public class ArgsDemo {
    public static void main(String[] args) {
        System.out.println(args[0]); // This program prints out the 0th command line argument
    }
    }
    ```

**Exercise 1.2.3:** 
- Try to write a program that sums up the command line arguments, assuming they are numbers. For a solution, see the webcast or the code provided on GitHub
    
```java
public class exer {
    public static void main(String[] args) {
        /* Assuming args is int*/
        int[] lst = new int[0];
        for (int i = 0; i < args.length(); i++) {
            int num = Integer.parseInt(args[i]);
            lst.append(num) // this is a bug
        }
        System.out.println(java.util.Arrays.stream(lst).sum());
    }
}
```
- Learned that we must convert args into an integer, since we have an array of Strings

**Solutions:**
```java
import java.util.Arrays;

public class exer {
    public static void main(String[] args) {
        System.out.println(Arrays.stream(args).mapToInt(Integer::parseInt).sum());
    } 
}

// cs61b solution

public class ArgsSum {
	/** Prints out the sum of arguments, assuming they are
 	*  integers.
 	*/
	public static void main(String[] args) {
    	int index = 0;
    	int sum = 0;
    	while (index < args.length) {
        	sum = sum + Integer.parseInt(args[index]);
        	index = index + 1;
    	}
    	System.out.println(sum);
	}
}

```

