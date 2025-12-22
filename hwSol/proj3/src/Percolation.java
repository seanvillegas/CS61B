import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * NOTES:
    grid[0][0] corresponds to (1, 1) in the project API
    grid[row-1][col-1] corresponds to (row, col)
    mapping the (row, col) position into a single index for the union–find structure:
    index = row × N + col (our spec is 0 based)
    Princeton’s spec (1-based): (row-1)*N + (col-1)
    union already tracks connected components via parent IDs
    after unions, connected tells you if they are in same component
 */

public class Percolation {
    private final int totalInput;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF connectivity;
    private final WeightedQuickUnionUF ufFull;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    private int cache;

    /**
     *        This is Weighted Disjoint Quick Union and Path Compression
     *        WeightedQuickUnionUF expects that implementation is constant
     *         // connect, isConnected, find, union // dont need to use find
     */
    public Percolation(int N) {
        if (0 >= N) {
            throw new IllegalArgumentException("Enter in positive integer");
        }
        totalInput = N;
        grid = new boolean[N][N];
        connectivity = new WeightedQuickUnionUF(N * N + 2); // 2 for virtual top/bottom
        ufFull = new WeightedQuickUnionUF(N * N + 1); // virtual top tracked
        virtualTopIndex = N * N;
        virtualBottomIndex = N * N + 1;
        cache = 0;
    }

    public void open(int row, int col) {
        if (!enforceValidIndex(row, col)) {
            throw new IndexOutOfBoundsException("Enter index within 0 to N - 1");
        }

        if (!isOpen(row, col)) {
            cache++;
        }
        grid[row][col] = true; // will be singleton on first open call

        if (row == 0) {
            // union with virtual top in both UF objects
            connectivity.union(xyTo1D(row, col), virtualTopIndex);
            ufFull.union(xyTo1D(row, col), virtualTopIndex);
        }

        if (row == totalInput - 1) {
            // union virtual bottom in connectivity only
            connectivity.union(xyTo1D(row, col), virtualBottomIndex);
        }

        // subsequent calls to open will require searching up, down left, right (4) times for adjacent connectedness
        /* check left neighbor and union in background iff neighbor isOpen. Similar logic for other methods below. */
        neighborUnionCheck(row, col, row, col - 1);
        /* check right neighbor */
        neighborUnionCheck(row, col, row, col + 1);
        /* check up neighbor */
        neighborUnionCheck(row, col, row - 1, col);
        /* check down neighbor */
        neighborUnionCheck(row, col, row + 1, col);
    }

    public boolean isOpen(int row, int col) {
        // return whats in the box
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // separate responsibility between grid structure and top sites
        // interact with disjoint data structure. Does the disjoint data structure return FULL?
        // BLUE represents when the site has water and if the disjoint data site look up is connected (i.e. its parents
        // are the same, then we implement isFull

        // what connected calls do I need to call to check isFull
        // brute force is manually check if every column is connected. Good for interview
        // "fudge" definition of data structure in ways that are convenient to speed things up
        // any time you open a top site, you union it to the virtual top site vice versa for bottom site
        // now for isFull, check for a connection to the top site

        // to check percolates, check for connection between top and bottom sites > enter backwash
        // much one smaller tweak
        return ufFull.connected(xyTo1D(row, col), virtualTopIndex);
    }

    public int numberOfOpenSites() {
        return this.cache;
    }

    public boolean percolates() {
        // interact disjoint data structure
        return connectivity.connected(virtualBottomIndex, virtualTopIndex);
    }

    // Helper Methods
    /**
     * Assign each square on the grid a number
     * Make it a one dimensional call so you can TRANSLATE its position to WeightedQuickUnion
     * @param r
     * @param c
     * @return
     */
    public int xyTo1D(int r, int c) {
        int oneDimensionIndex = r * totalInput + c;
        return oneDimensionIndex;
    }

    /**
     * If up, down, left, or right are NOT OOB, call xyTo1D for UNIONING two elements iff isOpen returns true for that
     * site
     */
    public void neighborUnionCheck(int row, int column, int rowCheck, int colCheck) {
        if (enforceValidIndex(rowCheck, colCheck)) {
            if (isOpen(rowCheck, colCheck)) {
                int openedCurrentSite = xyTo1D(rowCheck, colCheck);
                int lastOpenedSite = xyTo1D(row, column);
                connectivity.union(openedCurrentSite, lastOpenedSite);
                ufFull.union(openedCurrentSite, lastOpenedSite);
            }
        }
    }

    /**
     * Enforce valid indexing between 0 and N - 1 for all helper functions
     */
    public boolean enforceValidIndex(int row, int column) {
        return row >= 0 && column >= 0 && row < totalInput && column < totalInput;
    }

}
