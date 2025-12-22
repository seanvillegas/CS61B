import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0, // get min year, and max year. Even if no corresponding year sum element, just write the total.
        //           1992: 100
        //           1994: 600 // add their respective values for same key
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testList() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        assertEquals(catPopulation.years(), List.of(1991, 1992, 1994));
    }

    @Test
    public void quotientInvalidTest() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denom = new TimeSeries();

        numerator.put(1991, 10.0);
        numerator.put(1992, 100.0);
        numerator.put(1994, 200.0);
        numerator.put(2004, 400.0);
        // If TS is missing a year that exists in this TimeSeries, throw an IllegalArgumentException.
        numerator.put(2006, 400.0);
        numerator.put(2020, 400.0);


        denom.put(1991, 10.0);
        denom.put(1992, 4.3);
        denom.put(1994, 16.5);
        // this is allowed
        denom.put(1998, 203.2);
        denom.put(2004, 200.0);
        denom.put(2020, 55.1);

        assertThrows(IllegalArgumentException.class, () -> {
            numerator.dividedBy(denom);
        });

    }
    @Test
    public void quotientValidTest() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denom = new TimeSeries();

        numerator.put(1991, 10.0);
        numerator.put(1992, 100.0);
        numerator.put(1994, 200.0);
        numerator.put(2004, 400.0);
        numerator.put(2020, 400.0);


        denom.put(1991, 10.0);
        denom.put(1992, 4.3);
        denom.put(1994, 16.5);
        // this is allowed
        denom.put(1998, 203.2);
        denom.put(2004, 200.0);
        denom.put(2020, 55.1);

        List<Integer> expectedYears = List.of(1991, 1992, 1994, 2004, 2020);
        List<Double> expectedData = List.of(1.0, 23.255813953488374, 12.121212121212121, 2.0, 7.259528130671506);

        TimeSeries quotientTS = numerator.dividedBy(denom);
        assertEquals(quotientTS.years(), expectedYears);
        assertEquals(quotientTS.data(), expectedData);
        //System.out.println(quotientTS.data());
    }
}