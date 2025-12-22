package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

// No duplicates (you can check for yourself in the provided files).

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private Map<String, TimeSeries> wordsDS;
    private TimeSeries wordPerYearCount;
    private TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        // use IN to read in the files. Then create data structure:
        // TreeMap<String, TimeSeries> dataStructure = new TreeMap<>();
        In wordHistoryDF = new In(wordHistoryFilename);
        wordsDS = new TreeMap<>();

        // tab delimeter, white space, is read by all methods readInt etc. This is a more clear way of reading though:
        while (!wordHistoryDF.isEmpty()) {
            String nextLine = wordHistoryDF.readLine();
            String[] splitLine = nextLine.split("\t"); // word  year  numwordPerBook
            Integer year = Integer.parseInt(splitLine[1]);
            Double countPerYear = Double.parseDouble(splitLine[2]);

            if (!wordsDS.containsKey(splitLine[0])) {
                wordPerYearCount = new TimeSeries();
                wordPerYearCount.put(year, countPerYear);
                wordsDS.put(splitLine[0], wordPerYearCount);
            } else {
                wordsDS.get(splitLine[0]).put(year, countPerYear);
            }
        }

        In yearHistoryDF = new In(yearHistoryFilename);
        totalCounts = new TimeSeries();

        while (!yearHistoryDF.isEmpty()) {
            String nextLine = yearHistoryDF.readLine();
            // all data is read as strings at first and In doesnt read `,` as valid delimeter
            // year     totalNumWords from all texts that year
            String[] splitLine = nextLine.split(",");
            Integer year = Integer.parseInt(splitLine[0]);
            Double totalNumWordFromTextsYear = Double.parseDouble(splitLine[1]);
            totalCounts.put(year, totalNumWordFromTextsYear);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // object types are wrappers holding primitive values
        // implying calling TimeSeries will create a true copy
        if (!this.wordsDS.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(this.wordsDS.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // total history of word. Same steps as before just no bound
        // could simplify logic and call countHistory instead of repeating code.
        if (!this.wordsDS.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(this.wordsDS.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     * the total word occurrences per year, summed from all words — not the number of volumes (fourth column)
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(this.totalCounts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     * Get the TimeSeries for that word (its raw counts per year).
     * Get the totalCountHistory() — the total word counts per year (across all words).
     * Use your dividedBy method to compute: wordCounts.dividedBy(totalCounts)
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordCounts = this.countHistory(word, startYear, endYear);
        TimeSeries timeFrame = new TimeSeries(this.totalCountHistory(), startYear, endYear);
        return wordCounts.dividedBy(timeFrame);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordOfInterest = this.countHistory(word);
        TimeSeries totalTime = this.totalCountHistory();
        return wordOfInterest.dividedBy(totalTime);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sumRelativeFreq = new TimeSeries();

        for (String word : words) {
            TimeSeries iteration = weightHistory(word, startYear, endYear);
            sumRelativeFreq = sumRelativeFreq.plus(iteration);
        }
        return sumRelativeFreq;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
