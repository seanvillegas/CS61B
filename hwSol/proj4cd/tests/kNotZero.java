import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static main.Main.*;

public class kNotZero {

    /**
     * Data: word_history_eecs.csv, hyponyms_eecs.txt, synsets_eecs.txt.
     * Input: ["CS61A"], startYear = 2010, endYear = 2020, and k = 4
     * you should receive "[CS170, CS61A, CS61B, CS61C]"
     */
    @Test
    public void EECSTest() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(WORD_HISTORY_EECS_FILE,
                YEAR_HISTORY_FILE, SYNSETS_EECS_FILE, HYPONYMS_EECS_FILE);
        List<String> words = List.of("CS61A");
        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Data: word_history_size14377.csv, synsets_size82191.txt, and hyponyms_size82191.txt.
     * Input: words = "food, cake", startYear = 1950, endYear = 1990, and k = 5, then you would find the 5 most popular words in
     *          that time period that are hyponyms of both food and cake.

     * Here, the popularity is defined as the total number of times the word appears over the entire time period.
     *              The words should then be returned in alphabetical order. In this case, the answer is [cake, cookie, kiss, snap, wafer]
     */
    @Test
    public void bigDataMultiWordK() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE, SYNSETS_SIZE82191_FILE, HYPONYMS_SIZE82191_FILE);
        List<String> words = List.of("food", "cake");
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }
}
