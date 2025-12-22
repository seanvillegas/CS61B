package main;
import java.util.*;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {
    private NGramMap mapDS;
    private DiGraph DAG;
    private WordNet wn;

    /**
     * For autograder testing
     *
     *
     * While you are designing your implementation, keep in mind that we can give you words with the same frequencies.
     * If a word never occurs in the time frame specified, i.e. the count is zero, it should not be returned
     *          In other words, if k > 0, we should not show any words that do not appear in the ngrams dataset.
     *  If there are no words that have non-zero counts, you should return an empty list, i.e. [].
     *  if k != 0 then return the countHistory frequency of the hyponyms within year bounds, and only k returned
     *
     */
    public HyponymsHandler(String wordHistoryFile, String yearHistoryFile, String synsetFile, String hyponymFile) {
        this.mapDS = new NGramMap(wordHistoryFile, yearHistoryFile);
        this.wn = new WordNet(synsetFile, hyponymFile);
        this.DAG = wn.createGraph();
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int inputK = q.k();

        if (inputK == 0) {
            return wn.hyponyms(words).toString();
        } else {
            Set<String> hyponymIntersection = wn.hyponyms(words);
            if (!hyponymIntersection.isEmpty()) {
                List<String> topKFrequency = topKMinPQ(hyponymIntersection, startYear, endYear, inputK);
                //  avoid NPE with if condition to check that we arent passing in null, else return []
                if (!topKFrequency.isEmpty()) {
                    return sortOrder(topKFrequency);
                }
            }
            return "[]";
        }
    }

    /**
     * Helper that calculates each words frequency and puts in a Min PQ Map with the word and the frequency
     * Keep at most K. If we reach K, remove min and compare to current. If current greater, add.
     * If frequencies are the same, tie-break with alphabetical order
     * Using a PQ is O(log n) compared to a naive list of O(log) IMO, not confirmed.
     */
    public List<String> topKMinPQ(Set<String> hyponymsSet, int startYear, int endYear, int k) {
        PriorityQueue<WordFreq> atMostKPQ = new PriorityQueue<>();

        for (String word : hyponymsSet) {
            TreeMap<Integer, Double> currentWord = mapDS.countHistory(word, startYear, endYear);
            Double frequencyTotal = frequencyRange(currentWord);
            WordFreq currentPair = new WordFreq(word, frequencyTotal);

            if (frequencyTotal != 0.0) {
                if (atMostKPQ.size() != k) {
                    atMostKPQ.add(currentPair);
                } else {
                    WordFreq minWord = atMostKPQ.peek();
                    if (!validity(minWord, currentPair)) {
                        atMostKPQ.poll();
                        atMostKPQ.add(currentPair);
                    }
                }
            }
        }
        List<String> unsortedKPopularWords = new ArrayList<>();
        for (WordFreq pair : atMostKPQ) {
            unsortedKPopularWords.add(pair.word);
        }
        // for visualizing the frequencies of the words you should pass in WordFreq objects
        return unsortedKPopularWords;
    }

    /**
     * Lesson from SWE, write simple code
     * Tests, is the current minimum (contender) better than the prospect(current)?
     */
    private boolean validity(WordFreq contendor, WordFreq prospect) {
        Double contestantFrequency = contendor.freq;
        String contestantWord = contendor.word;
        Double prospectFrequency = prospect.freq;
        String prospectWord = prospect.word;

        // we can trust abstraction for compareTo with mixed strings (i.e. with ints)
        if (contestantFrequency > prospectFrequency || prospectFrequency == contestantFrequency
                && prospectWord.compareTo(contestantWord) < 0) {
            // thus the contestant word freq is still larger and should not be replaced by prospect
            // OR frequencies are the same, so we compare alphabetically if contestant is alphabetically less.
            // If it is, keep contestant
            return true;
        }
        // prospect word has greater frequency, or its word is alphabetically less than contestant
        return false;
    }

    /**
     * Helper that takes input of unsorted top k elements, and returns them sorted alphabetically
     * Making a merge sort (where you split it up left and right and add the total from ascending order
     * is the manual way)
     * This method is destructive
     */
    public String sortOrder(List<String> unsortedTopK) {
        unsortedTopK.sort(String::compareTo);
        String sortedOrder = unsortedTopK.toString();
        return sortedOrder;
    }

    /**
     * Helper method that returns the frequency within the time range
     */
    public Double frequencyRange(TreeMap<Integer, Double> range) {
        Double runningTotal = 0.0;
        for (Double i : range.values()) {
            runningTotal += i;
        }
        return runningTotal;
    }

    /**
     * Map is a little clunky to use, so create custom data structure that acts as a javafx.util.Pair
     * You need to implement Comparable so that PriorityQueue knows how to compare objects and sort the heap
     */
    private static class WordFreq implements Comparable<WordFreq> {
        String word;
        double freq;

        WordFreq(String word, double freq) {
            this.word = word;
            this.freq = freq;
        }

        @Override
        public int compareTo(WordFreq o) {
            int freqCompare = Double.compare(this.freq, o.freq);
            if (freqCompare != 0) {
                return freqCompare;
            }
            // alphabetical tie breaker
            return this.word.compareTo(o.word);
        }
    }
}
