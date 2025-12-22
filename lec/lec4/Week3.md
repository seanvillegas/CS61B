## Test advice


1. Understand writing tests with J.unit syntax
2. Do homeworks and projects so you can ace the exam is their goal this semester



- exam type questions ,pre
- bridge discussion, 2 hour section


3 Remove Duplicates 
    - that code is what should be on your project


## Project

1. Cant submit to the autograder over 4 times a day
2. Some tests, just need to write some tests. Big part of it needs to have tests
    - is there some way to capture how the tester is being evaluated??
3. Getting Started Video from Hug
4. Once you change the previous 

#### for this project, you are required to implement a circular, doubly-linked topology with a sentinel.




- in the LinkedListDeque61B
- add implements Deque61B<T>
    - you will get an error, you must have all of the methods by hovering over the error 
- add empty constructor 
- need to create node class 
- sentinel as the invariant (middle man)
- recursive because the pointer points at a node? 
- sentinel should be pointing to its next and its prev should point to its next so when you add to it, it expands
- addFirst and addLast require you to edit sentinel.next pointer to the new addFirst or addLast. That also requires you to preserve the original sentinel.next/prev so that you dont lose that pointer and variable
- empty is just size == 0 
- caching for size 
- getRecursive needs a helper method 
- get will be infinite? 
- OOB, do size - 1 if check for base if it doesnt exist should spit something out 

## H6 Debugging


A = {2, 0, 10, 14}  
B = {-5, 5, 20, 30}  
element-wise max = {max(2, -5), max(0, 5), max(10, 20), max(14, 30)}  
                 = {2, 5, 20, 30}

2 + 5 + 20 + 30 = 57

**What element wise means:** 
- Instead, it means: look at each pair and pick the larger one.
- Element-wise max (what your question is about): take the larger of the two numbers at each position.


The sumOfElementwiseMax method in MachineStage is supposed to take two arrays, compute the element-wise max of those two arrays, and then sum the resulting maxes. For example, for two arrays {2, 0, 10, 14} and {-5, 5, 20, 30}, the element-wise max is {2, 5, 20, 30}. In the second position, the larger of 0 and 5 is 5. The sum of this element-wise max is `2+5+20+30=57`

- When adding I got -6

-3 + 5

2 + 20 + 30 52



1, -10, 3
0, 20, 5

1, 20, 5

These are XOR correct? 
SO therefore wouldnt I NEED to inspect mysteryMax??? 
0 -10 3


mysteryMax needs to be altered

mysteryAdd

moral: step through debugger and check result, if fish is dead we can tell without understanding deeper level; just by looking at fish and it having no movement


    /**
     * Returns the maximum integer between a and b.
     */
    public static int mysteryMax(int a, int b) {
        // Omit
        int w = (b - a) >> 31;
        int z = ~(b - a) >> 31;

        int max = b & w | a & z;
        return max;
    }

    /**
     * Returns the sum of integers a and b.
     */
    public static int mysteryAdd(int a, int b) {
        // Omit
        int x = a, y = b;
        int xor, and, temp;
        and = x & y;
        xor = x ^ y;

        while (and != 0) {
            and <<= 1;
            temp = xor ^ and;
            and &= xor;
            xor = temp;
        }
        return xor;
    }
