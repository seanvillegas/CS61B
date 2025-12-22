package main;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     * HashMap type, store integer for year that occurs once
     * Within that HashMap, store a Map of String to Integer. Take a count of each time that unique string occurs
        * WordsCountMap is a TreeMap<String, Integer>.
     */
    public TimeSeries() {
        super(); // TimeSeries objects should have no instance variables.
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        NavigableMap<Integer, Double> newTs  = ts.subMap(startYear, true, endYear, true);
        putAll(newTs);
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        // return new ArrayList<>(this.keySet()); // has the same effect as manually doing an iterator. With less code.
        List<Integer> yearList = new ArrayList<>();

        Set<Integer> yearListAscending = this.keySet();
        Iterator<Integer> oNTime = yearListAscending.iterator();

        while (oNTime.hasNext()) {
            yearList.add(oNTime.next());
        }
        return yearList;
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        return new ArrayList<>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // brute force: combine both years and pull the corresponding values based on the keys in the list

        TimeSeries sumTs = new TimeSeries();

        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            if (ts.containsKey(entry.getKey())) {
                Integer match = entry.getKey();
                Double addValues = entry.getValue();
                sumTs.put(match, addValues + ts.get(match));
            } else {
                sumTs.put(entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            if (!this.containsKey(entry.getKey())) {
                sumTs.put(entry.getKey(), entry.getValue());
            }
        }

        return sumTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     * You may assume that the dividedBy operation never divides by zero.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // Conceptually, dividedBy is treating this as the “numerator” and ts as the “denominator.”
        TimeSeries quotientSeries = new TimeSeries();

        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            if (ts.containsKey(entry.getKey())) {
                Integer match = entry.getKey();
                Double numerator = entry.getValue();
                quotientSeries.put(match, numerator / ts.get(match));
            } else {
                throw new IllegalArgumentException("TS is missing a year that exists in this TimeSeries");
            }
        }

        return quotientSeries;
    }
}
