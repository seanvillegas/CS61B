package main;

import edu.princeton.cs.algs4.In;
import java.util.SortedSet;

import java.util.*;

public class WordNet {
    // vertices
    Map<Integer, List<String>> idToWords = new HashMap<>();
    // lookup table
    Map<String, List<Integer>> wordsToIds = new HashMap<>();
    // edges
    Map<Integer, List<Integer>> hypernymToHyponym = new HashMap<>();
    DiGraph DAG;

    public WordNet(String synsetsFile, String hyponymsFile) {
        In synsets = new In(synsetsFile);

        while (!synsets.isEmpty()) {
            String nextLine = synsets.readLine();
            String[] splitLine = nextLine.split(","); // synset ID, synset, omit definition
            Integer synsetID = Integer.parseInt(splitLine[0]);
            List<String> wordSet = parseSynsets(splitLine[1], " ");
            idToWords.put(synsetID, wordSet);
        }

        for (Map.Entry<Integer, List<String>> eSet : idToWords.entrySet()) {
            for (String word : eSet.getValue()) {
                if (wordsToIds.containsKey(word)) {
                    wordsToIds.get(word).add(eSet.getKey());
                } else {
                    List<Integer> dummy = new ArrayList<>();
                    dummy.add(eSet.getKey());
                    wordsToIds.put(word, dummy);
                }
            }
        }

        In hypernymToHyponymIn = new In(hyponymsFile);

        while (!hypernymToHyponymIn.isEmpty()) {
            String nextLine = hypernymToHyponymIn.readLine();
            String[] splitLine = nextLine.split(","); // synset ID, ID of direct hypernyms
            Integer synsetID = Integer.parseInt(splitLine[0]);
            List<Integer> listOfHyponyms = parseSynsetID(splitLine);
            if (hypernymToHyponym.containsKey(synsetID)) {
                // forEachOrdered collections.stream() for elements in a list to add in another list under hood
                listOfHyponyms.stream().
                        forEachOrdered(hypernymToHyponym.get(synsetID)::add);
            } else {
                hypernymToHyponym.put(synsetID, listOfHyponyms);
            }
        }

        DAG = createGraph();
    }

    /**
     * The hypernym to hyponym relationship becomes directed edges in the DAG.
     * If synset H is a hypernym of synset h1 and h2, you add edges:
     *      addEdge(H, h1), addEdge(H, h2)
     * The DAG is just the graph of IDs, the WordNet data structures handle translating IDs ↔ words.
     *
     */
    public DiGraph createGraph() {
        // idToWords.size() is going to create an OOB instead take the max keySet
        int maxID = Collections.max(idToWords.keySet());
        DAG = new DiGraph(maxID + 1);

        for (Map.Entry<Integer, List<Integer>> eSet : hypernymToHyponym.entrySet()) {
            for (Integer hyponym : eSet.getValue()) {
                // even if one hypernym maps to many hyponyms, each hyponym is its own vertex.
                // You need multiple edges, not one grouped node, because each edge represents
                // separate directional relationship in the semantic hierarchy.
                DAG.addEdge(eSet.getKey(), hyponym);
            }
        }
        return DAG;
    }

    /**
     * Multi Word method
     * Returns the word, and then the intersection of the hyponyms, never union. The order is enforced alphabetically.
     */
    public Set<String> hyponyms(List<String> words) {
        // base cases
        if (words.isEmpty()) {
            return new TreeSet<>();
        }

        if (words.size() == 1 && isValid(wordsToIds, words.get(0))) {
            return hyponyms(words.get(0));
        }

        // enter multi word case
        // enforce validity, we only want hyponyms of ALL words that user passes in
        // pass 3.6 non-existent hyponym test
        for (String word : words) {
            if (!isValid(wordsToIds, word)) {
                return new TreeSet<>();
            }
        }
        // multi word case
        Set<String> result = hyponyms(words.get(0));
        // multi word case

        for (int i = 1; i < words.size(); i++) {
            // in multi word case, dont run DFS on a word not present in our maps (i.e. call isValid method)
            Set<String> currentSet = hyponyms(words.get(i));
            result = intersection(result, currentSet);
            if (result.isEmpty()) {
                return result; // early exit running result if the next word is not returning intersecting hyponyms
            }
        }
        // return intersection of the N words passed in
        return result;
    }

    /**
     * Single Word overridden method
     */
    public Set<String> hyponyms(String word) {
        SortedSet<String> topHyponymsWords = new TreeSet<>();
        List<Integer> idsOfInterest;
        if (isValid(wordsToIds, word)) {
            idsOfInterest = wordsToIds.get(word);
        } else {
            // in single word case, we dont want to run DFS if the word is not valid.
            return topHyponymsWords;
        }
        DirectedDFS dfsObject = new DirectedDFS(DAG, idsOfInterest);
        Set<Integer> topHyponymsID = dfsObject.markedVertices();

        for (Integer i : topHyponymsID) {
            List<String> currentWords = idToWords.get(i);
            for (String currWord : currentWords) {
                topHyponymsWords.add(currWord);
            }
        }
        return topHyponymsWords;
    }

    // helper methods

    /**
     * Computes the intersection between two sets
     */
    private Set<String> intersection(Set<String> set1, Set<String> set2) {
        // Retain elements that are also in set2 (i.e. for duplicates, retain them, else discard)
        // if no hyponyms exist, retainAll will return an empty set
        set1.retainAll(set2);
        return set1;
    }

    /**
     * Parser for working with synsets.txt
     */
    private List<String> parseSynsets(String words, String delimiter) {
        String[] capture  = words.split(delimiter);
        //NOTE: returns fixed sized list. You cant add or remove later
        return Arrays.asList(capture);
    }

    /**
     * Parser for working with hyponyms.txt
     */
    private List<Integer> parseSynsetID(String[] words) {
        List<Integer> streamMap = new ArrayList<>();

        // instead of creating copy of an array which creates overhead cost just start index at 1.
        for (int i = 1; i < words.length; i++) {
            streamMap.add(Integer.parseInt(words[i]));
        }
        return streamMap;
    }

    /**
     * Enforce validity of look ups
     */
    private boolean isValid(Map someMap, String K) {
        // O(N) on lists, on Maps O(log N)
        return someMap.containsKey(K);
    }

}
