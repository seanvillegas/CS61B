import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.OptionalInt;

public class JavaExercises {
    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        /**
         * Return an array of a dice numbers. The method returns an int. The lesson is to convert an ArrayList object to
         * primitive data-type
         *
        */
        ArrayList<Integer> dice = new ArrayList<Integer>();
        dice.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)); // asList creates constraint // addAll returns to resizable
        int[] compatible = new int[6];
        for (int i = 0; i < dice.size(); i++) {
            compatible[i] = dice.get(i); 
        }
        return compatible;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        /**
         * Based on the string passed in return their string list data type, else return null 3 times.
         * This problem is a little confusing to understand, I think it would be better to say return:
         *          [beyti, pizza, hamburger, tea]
         *          instead of 'return ["beyti", "pizza", "hamburger", "tea"]'
         *          my first attempt was creating a map data type
         */
        String ergun = "Ergun";
        String erik = "Erik";
        String[] ergunLst = {"beyti", "pizza", "hamburger", "tea"};
        String[] erikLst = {"sushi", "pasta", "avocado", "coffee"};
        String[] err = new String[3];
        if (customer.equals(ergun)) { // dont use ==
            return ergunLst;
        } else if (customer.equals(erik)) {
            return erikLst;
        } else
            return err;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        /**
         * Return the positive difference between max and min integer of param array
         * I solved using objects, instead of the boomer way of manually looping through each primitive value int,
         * comparing said int and then saving the max/min to subtract.
         */
        OptionalInt maxNum = Arrays.stream(array).max();
        OptionalInt minNum = Arrays.stream(array).min();
        int maxInt = maxNum.getAsInt();
        int minInt = minNum.getAsInt();
        int positiveDifference = maxInt - minInt;
        return positiveDifference;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        assert x != 0 : "Pls pass in a non zero int";
        list.add(x);
        while (x != 1) {
            if (x % 2 == 0) {
                x = x / 2;
            } else {
                x = 3 * x + 1;
            }
            list.add(x);
        }
        return list;
    }

}
