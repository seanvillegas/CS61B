package game2048logic;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 0, 0},
                {2, 0, 4, 8},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        System.out.println("Initial board:");
        printBoard(board);

        // Example test call (after you implement the method)
        GameLogic.tiltUp(board);

        System.out.println("Board after tiltUp:");
        printBoard(board);
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}


