package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap mapDS;

    public HistoryHandler(NGramMap mapDS) {
        this.mapDS = mapDS;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        ArrayList<TimeSeries> lts = new ArrayList<>();

        for (String word : words) {
            lts.add(mapDS.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String s = Plotter.encodeChartAsString(chart);
        //XYChart display = displayChart(chart);
        return s;
    }
}
