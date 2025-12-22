import java.util.Comparator;
import java.util.List;

public class WordComparators {
    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int countObj1 = xHelper(o1);
                int countObj2 = xHelper(o2);
                return countObj1 - countObj2;
            }
        };
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int countObj1 = charHelper(o1, c);
                int countObj2 = charHelper(o2, c);
                return countObj1 - countObj2;
            }
        };
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending).
     * getCharListComparator(List.of('a', 'e', 'i', 'o', 'u')) should return
        * a comparator that compares based on the number of lowercase vowels in the string.
     * */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int charSeqCheck1 = charListHelper(chars, o1);
                int charSeqCheck2 = charListHelper(chars, o2);
                return charSeqCheck1 - charSeqCheck2;
            }
        };
    }

    // Helper functions
    private static int xHelper(String xCheck) {
        int tracker = 0;
        for (int i = 0; i < xCheck.length(); i++) {
            if (xCheck.charAt(i) == 'x') {
                tracker++;
            }
        }
        return tracker;
    }

    private static int charHelper(String charCheck, char ch) {
        int charTracker = 0;

        for (int i = 0; i < charCheck.length(); i++) {
            if (charCheck.charAt(i) == ch) {
                charTracker++;
            }
        }
        return charTracker;
    }

    private static int charListHelper(List<Character> chars, String multipleCharCheck) {

        int charSeqMatch = 0;
        char[] charListSeq = multipleCharCheck.toCharArray();
        for (char c : chars) {
            for (char z : charListSeq) {
                if (c == z) {
                    charSeqMatch++;
                }
            }
        }
        return charSeqMatch;
    }
}
