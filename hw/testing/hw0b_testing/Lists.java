import java.util.List;
import java.util.ArrayList;

public class Lists {
    public static void main(String[] args) {
        // Problem 1
            // List<Integer> lst = List.of(1, 2, 3, 4);
            // System.out.println(sum(lst));
        // Problem 2
            // List<Integer> lst = List.of(1, 2, 3, 4, 5, 6);
            // System.out.println(evens(lst));
        // Problem 3
            //List<Integer> lst1 = List.of(1, 2, 3, 4, 5, 6);
            //List<Integer> lst2 = List.of(4, 5, 6, 7, 8, 9);
            //System.out.println(common(lst1, lst2));
        // Problem 4
            List<String> lst = List.of("hello", "world", "welcome");
            System.out.println(countOccurrencesOfC(lst, 'a'));
    }

    public static int sum(List<Integer> L) {
        /** Returns the total sum in a list of integers
         * Can solve with objects or boomer way
         * */
        int runningTotal = 0;
        for (int i = 0; i < L.size(); i++) {
            int current = L.get(i);
            runningTotal += current;
        }
        return runningTotal;
    }

    public static List<Integer> evens(List<Integer> L) {
        /**
         * Returns a list containing the even numbers of the given list
         */
        List<Integer> otherL = new ArrayList<>();
        for (int i : L) { // enhanced for loop auto-unboxes the primitive type
            if (i % 2 == 0) {
                otherL.add(i);
            }
        }
        return otherL;
    }

    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        /**
         *  Returns a list containing the common item of the two given lists, if no common types
         * return empty list */
        List<Integer> newL = new ArrayList<>();
        for (int i : L1) {
            if (L2.contains(i)) {
                newL.add(i);
            }
        }
        return newL;
    }
    
    public static int countOccurrencesOfC(List<String> words, char c) {
        /** Returns the number of occurrences of the given character in a list of strings.
         * This method takes a list and a character List<String> words, char c
         * and returns the number of occurrences of the given character in a list of strings.
         *
         * If the character does not occur in any of the words, it should return 0.*/
        int count = 0;
        StringBuilder fix = new StringBuilder();
        for (String i : words) {
            fix.append(i);
        }
        char[] compatible = fix.toString().toCharArray();
        for (char i : compatible) {
            if (i == c) {
             count++;
            }
        }
        return count;
    }
}