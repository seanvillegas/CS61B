public class ArrayExercises {
    /** Returns the second to last item in the given array.
     *  Assumes the array has at least 2 elements. */
    public static String secondToLastItem(String[] items) {
        int captureLen = items.length - 1;
        int secondToLast = captureLen - 1;
        return items[secondToLast];
    }    

    /** Returns the difference between the minimum and maximum item in the given array */
    public static int minMaxDifference(int[] items) {
        int keepMin = items[0];
        int keepMax = items[0];

        for (int i = 0; i < items.length; i++) {
            if (keepMin > items[i]) {
                keepMin = items[i];
            } else if (keepMax < items[i]) {
                keepMax = items[i];
            }
        }
        return keepMax - keepMin;
    }
}
