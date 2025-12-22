import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.WordNet;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests the case where the list of words is length greater than 1, but k is still zero.
 * The word history and year history files do not matter for the k==0 case, but are provided
 * as input for the constructor of the HyponymsHandler.
 */
public class TestMultiWordK0Hyponyms {
    private static final String PREFIX = "./data/";

    /** NGrams Files */
    public static final String WORD_HISTORY_EECS_FILE = PREFIX + "word_history_eecs.csv";
    public static final String WORD_HISTORY_SIZE3_FILE = PREFIX + "word_history_size3.csv";
    public static final String WORD_HISTORY_SIZE4_FILE = PREFIX + "word_history_size4.csv";
    public static final String WORD_HISTORY_SIZE1291_FILE = PREFIX + "word_history_size1291.csv";
    public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
    public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";

    /** Wordnet Files */
    public static final String SYNSETS_EECS_FILE = PREFIX + "synsets_eecs.txt";
    public static final String HYPONYMS_EECS_FILE = PREFIX + "hyponyms_eecs.txt";
    public static final String SYNSET_SIZE16_FILE = PREFIX + "synsets_size16.txt";

    // full file test
    public static final String SYNSETS_SIZE82181_FILE = PREFIX + "synsets_size82191.txt";
    public static final String HYPONYMS_SIZE82191_FILE = PREFIX + "hyponyms_size82191.txt";

    public static final String HYPONYM_SIZE16_FILE = PREFIX + "hyponyms_size16.txt";
    public static final String SYNSET_SIZE1000_FILE = PREFIX + "synsets_size1000.txt";
    public static final String HYPONYM_SIZE1000_FILE = PREFIX +  "hyponyms_size1000.txt";


    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE, SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
        List<String> words = new ArrayList<>();
        words.add("occurrence");
        words.add("change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void multiWordLargeData() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE, SYNSETS_SIZE82181_FILE, HYPONYMS_SIZE82191_FILE);
        // why isEqualTo giving the error: Cannot resolve method 'isEqualTo' in 'Set'
        // Set.equals checks for same elements ignoring order, so it works perfectly here.
        // need to call containsExactly inOrder on a list
        // if you find yourself asserting order, that’s often a design smell that a list or sequence type is a better fit.
        // Sets are inherently order-independent, thus calling inOrder, isEqualTo wont work because that enforces order
        List<String> words = List.of("video", "recording");
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[video, video_recording, videocassette, videotape]";

        assertThat(actual).isEqualTo(expected);
    }

}
