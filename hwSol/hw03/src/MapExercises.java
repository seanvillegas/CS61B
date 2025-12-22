import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    static final int ASCII_A = 97;
    static final int ASCII_Z_PLUS_ONE = 123;

    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> storeMe = new TreeMap<>();

        for (int i = ASCII_A; i < ASCII_Z_PLUS_ONE; i++) {
            char compat = (char) i;
            int trackMe = i - ASCII_A + 1;
            storeMe.put(compat, trackMe);
        }
        return storeMe;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> arithmetic = new TreeMap<>();

        for (Integer i: nums) {
            Integer val = i * i;
            arithmetic.put(i, val);
        }
        return arithmetic;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> worde = new TreeMap<>();

        for (int z = 0; z < words.size(); z++) {
            String complaint = words.get(z);
            int count = 0;
            for (String word: words) {
                if (complaint == word) {
                    count++;
                    worde.put(word, count);
                }
            }
        }
        return worde;
    }
}
