# Exam Walk Through
#### Sean Villegas


## TRS September 19, 25
### Reptile Room

Moral:
1. In your class body, take note of the instance variables that your constructor will call.
2. If any of them are public static, that means they are accessible by instance variables and the class itself
3. Thus, creating an instance with that static variable will update it for all instance variables to see. 
4. Calling locationName.location after instantiating new instances of the class will update it.
5. When visualizing, take note that the instances will NOT have their own locations within the memory box. 

### ARRAYana Grande
1. You can access arrays and instantiate new arrays with for loops, at the parent (not an official term, one I made up) array index. 
2. You can access that arrays internal array as well. 

### Transpose
1. You need to understand that 2D arrays have type int[][], and when instantiating a triangular 2d array grid you must use syntax like this:

    `int[][] transpose = new int[arbitraryArr[0].length][];`
2. for `int[][]` type, the first access will grab the row, the second array access will grab the column
3. Everytime there is an if check on these questions its a base case to return
4. T