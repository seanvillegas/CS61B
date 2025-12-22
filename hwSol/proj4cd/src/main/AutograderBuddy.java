package main;

import browser.NgordnetQueryHandler;

public class AutograderBuddy {
    /**
     * Returns a HyponymsHandler
     */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {

        return new HyponymsHandler(wordHistoryFile, yearHistoryFile, synsetFile, hyponymFile);
    }
}
