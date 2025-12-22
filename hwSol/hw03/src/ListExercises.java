import java.util.ArrayList;
import java.util.List;

public class ListExercises {
    public static void main(String[] args) {

    }
    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int total = 0;

        if (L.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < L.size(); i++) {
            Integer curr = L.get(i);
            total += curr;
        }
        return total;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> newEven = new ArrayList<>();

        for (int i = 0; i < L.size(); i++) {
            if (L.get(i) % 2 == 0) {
                newEven.add(L.get(i));
            }
        }
        return newEven;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> commonList = new ArrayList<>();

        for (int i: L1) {
            if (L2.contains(i)) {
                commonList.add(i);
            }
        }
        return commonList;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int totalOccurences = 0;

        for (String i: words) {
            char[] step = i.toCharArray();

            for (char z: step) {
                if (c == z) {
                    totalOccurences++;
                }
            }
        }
        return totalOccurences;
    }
}
