import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Comparator;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     * In english:
     *  e.g., if a user passed in "m" then they want this method to find the **string** that has the most "m"
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        String maxString = strings[0];
        for (int i = 1; i < strings.length; i++) {
            String current = strings[i];
            if (c.compare(maxString, current) < 0) {
                maxString = current;
            }
        }
        return maxString;
    }

    // helper function that filters out special characters and allows capital first letter
    private static boolean assertLCWord(String str) {
        char[] forbidden = {'-', '?', ';'};
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (char f : forbidden) {
                if (ch == f) {
                    return false;
                }
            }
            if (i == 0) {
                if (!Character.isLowerCase(ch) && !Character.isUpperCase(ch)) {
                    return false;
                }
            } else {
                if (!Character.isLowerCase(ch)) {
                    return false;
                }
            }
        }
        // everything passed
        return true;
    }

    // helper function that counts amount of lower case vowels
    private static int countLowerCaseVowels(String str) {
        char[] vowelsLowerCase = {'a', 'e', 'i', 'o', 'u'};
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            for (char z : vowelsLowerCase) {
                if (z == str.charAt(i)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Fill in the main method so that only the word with the most lowercase vowels is printed out.
     */
    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        //       Start by creating a Comparator that compares based on lower case vowels.
        Comparator<String> vowelComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int vowelO1Count = countLowerCaseVowels(o1);
                int vowelO2Count = countLowerCaseVowels(o2);
                return vowelO1Count - vowelO2Count;
            }
        };

        ArrayList<String> filteredList = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (assertLCWord(words[i])) {
                filteredList.add(words[i]);
            }
        }

        String[] compatible = filteredList.toArray(String[]::new);
        System.out.println(findMax(compatible, vowelComparator));
    }
}
