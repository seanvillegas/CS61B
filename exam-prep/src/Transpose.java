public class Transpose {
    /**
     * Non destructively transpose A. Assume A is triangular
     */
    public static int[][] transposeTriangular(int[][] arr) {
        if (arr.length == 0) {
            return new int[0][];
        }
        int[][] transposeArr = new int[arr[0].length][];
        for (int i = 0; i < transposeArr.length; i++) {
            int rowLen = arr.length - i;
            transposeArr[i] = new int[rowLen];
            for (int j = 0; j < rowLen; j++) {
                transposeArr[i][j] = arr[j][i];
            }
        }
        return transposeArr;
    }

    public static void main(String[] args) {
        int[][] exampleArr = new int[4][];
        exampleArr[0] = new int[]{1, 2, 3, 4};
        exampleArr[1] = new int[]{5, 6, 7};
        exampleArr[2] = new int[]{8, 9};
        exampleArr[3] = new int[]{10};
        System.out.println("HELP");

        transposeTriangular(exampleArr);
    }
}

