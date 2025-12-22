## IntList Assignment


## NonDestructive
1. Create a new object and return the modified new list
2. Traverse through it, tail to head

```java
    public static IntList incrRecursiveNondestructive(IntList L, int x) {
        // manually build new nodes as you traverse through L. Primitive int will be deep copy and rest will be shallow
            // so how do you do that recursively for the reference
            // the below approach is working with ints
        int traverse = L.size();
        IntList z;

        IntList p = L;
        while (p != null) {
            z = new IntList(p.first + x, p.rest);
            p = p.rest;
            return z;
        }
//        for (int idx = 0; idx < traverse; idx++) {
//            int current = L.get(idx); // grabs the int, so you cant work with the object
//            System.out.println(current);
//        }
        return z;
    }


    /** 
    
    First attempt was using .get method of the IntList class. The error behind that is you CANNOT access the reference (i.e. object). get will return the int of the first call

    Second attempt was helped by AI
    IntList pointer = L;
    while (pointer != null) {
        System.out.println(pointer.first);  // value of current node
        pointer = pointer.rest;             // move to the next node
    }

    This seems to be a common pattern of pointer != null
    Issue was that it would be an infinite loop because the pointer would always poing to the first pointer.rest


How to solve: 
        // manually build new nodes as you traverse through L. Primitive int will be deep copy and rest will be shallow
        // so how do you do that recursively for the reference
        // the below approach is working with ints

    */
```


## Destructive
1. Traverse through the list, do not use new since we are accessing the current object that is passed in as a parameter (literally copying the bits so we access it)

```java

public static IntList incrRecursiveDestructive(IntList L, int x) { 
    if (L.rest == null) { // tail
        L.first = L.first + x
    } else if (L.rest != null) { // supposed to be head case 
        incrRecursiveDestructive(L.rest, x); 
    } 
    return L;
    
    // ai direction

        if (L.rest != null) {
            L.first = L.first + x;
            incrRecursiveDestructive(L.rest, x);
        } else if (L.rest == null) {
            L.first = L.first + x;
        }
        return L;
}
```

- I notice I can modify in place the tail, and if I change the first if statement to != it will modify the head. However, I cant seem to modify both, why is that?
    - **Answer** you hit the else if statement and then hit the base, and update base, without updating the original. Simply adding the same L.first update statement will fix that.
- A note on the structured logic you did //ai direction
    - This code hits else if statement and we have two update lines. We can just return null if L is empty upon each increment when we hit the base case, and instead modify in place at all later calls and then recurse deeper
- Modifying head to tail is fine for quick accessing, but here is tail to head solution: 
    ```java
    function incrRecursiveDestructiveTail(L, x):
    if L is null:
        return null
    // first recurse to the end
    incrRecursiveDestructiveTail(L.rest, x)
    // now modify current node
    L.first = L.first + x
    ```


## Destructive Not Recursive

```java

        int iter = L.size();
        int placeHolder = 0;
        IntList z = new IntList(placeHolder, null);

        for (int idx = 0; idx < iter; idx++) {
            if (L.rest == null) {
                z.first = L.first + x;
            } else {
                z.first = L.first + x;
                L = L.rest;
            }
        }


    // code that rewrites first but will traverse tell end
        public static IntList incrIterativeNondestructive(IntList L, int x) {
        int iter = L.size();
        int placeHolder = 0;
        IntList z = new IntList(placeHolder, null);

        for (int idx = 0; idx < iter; idx++) {
            if (L.rest == null) {
                return z;
            } else if (L != null) {
                z.first = L.first + x;
                L = L.rest;
                z.rest = new IntList(L.first + x, null);
            }
        }
        return z;
    }
```



## Solving with iteration

1. `clone()` only helps with Java’s built-in `LinkedList`, and it’s shallow.
2. You must hit the base case to get through the full list, so when the list is null
3. Example of iteration, modifying in place, and returning the correct list



### Testing notes:

```txt

// pseudocode 
dummy = new IntList(0, null)   // placeholder node
tail = dummy
curr = L

while curr != null:
    tail.rest = new IntList(curr.first + x, null)
    tail = tail.rest
    curr = curr.rest

return dummy.rest


    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L == null) {
            return null;
        }
        incrRecursiveDestructive(L.rest, x);
        L.first = L.first + x;
        return L;
    }


  //// attempt 1

      public static IntList incrIterativeDestructive(IntList L, int x) {
          IntList tail = L.rest;
          while (L != null) {
              L.first = L.first + x;
              if (tail != null) {
                  tail = tail.rest;
              } else {
                  L.first = L.first + x;
                  return L;
              }
              L = L.rest;
          }
          return L;
      }

    public static IntList incrIterativeDestructive(IntList L, int x) {
        IntList tail = L.rest;
        while (L != null) {
            L.first = L.first + x;
            if (tail != null) {
                tail = tail.rest;
            } else {
                tail = null;
                return L;
            }
            L = L.rest;
        }
        return L;
    }

// create pointer to the original L for that point in time for memory

// questions

do I have another pointer on it that iterates through it and writes to it?

// task 5

    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L == null) {
            return null;
        }
        incrRecursiveDestructive(L.rest, x);
        L.first = L.first + x;
        return L;
    }

   // do you recommend going from tail to head for this problem
   // Do I iterate through both of them or do I iterate through L2 completely
        then iterate through L1 to reach its last element

       1. The solution is you dont need to iterate through L2 all the way, but rather
            take the head pointer and add it to the end of the list and
            java will copy all that memory and pointers
```