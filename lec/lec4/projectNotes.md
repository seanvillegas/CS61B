## Project Notes

**GOAL:** Implement Double Linked Sentinel List 


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



### Approach for Project
1. Right now, SLList method addLast is slow because we recurse through it completely 
2. The solution to that is to have a pointer at the start of the line, that points to the end of the line, and the end pointer points to the beginning of the list. Creating a circle
    - as you add elements to the list, the double linked list pointer is preserved in memory and elements are added to the middle
    - this approach is the double linked lists (double sentinel)
    - the monk approach: is to have a sentinel as both first and last of the list
    
### **READ THIS**
-  whenever you have an empty list, the previous is sentinel itself, and the next is the sentinel itself



**A note on adding a pointer to last element in the list**
1. addLast, getLast, size will be fast thanks to caching 
2. However, removeLast is slow because: *you need to fix the pointers to second last item*
    - approaching with caching, is not the correct approach because we can hit the problem of getting nth to last item
3. **Solution:** is to have a previous memory box pointer that points to the predecessor, and the next pointing to the next predecessor
