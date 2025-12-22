package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

/**
 * handle must get access to NGramMap so it can call methods
 * Interface Route comes from spark
 */
public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap mapDS;

    // create constructor

    public HistoryTextHandler(NGramMap mapDS) {
        this.mapDS = mapDS;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder returnSB = new StringBuilder();

        for (String word : words) {
            TimeSeries get = mapDS.weightHistory(word, startYear, endYear);
            if (get == null) {
                get = new TimeSeries();
            }
            returnSB.append(word + ": " + get.toString() + "\n");
        }
        return returnSB.toString();
    }
}
