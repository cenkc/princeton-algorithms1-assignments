package main.java;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * created by cenkc on 5/17/2020
 */
public class Percolation {

    private int N;
    private boolean[] track;
    private WeightedQuickUnionUF percolationWQUF;
    private WeightedQuickUnionUF fullCheckWQUF;
    private final int VIRTUAL_TOP;
    private final int VIRTUAL_BOTTOM;
    private int counter = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
        this.N = n;
        this.track = new boolean[n*n+2]; // they're all false by default
        VIRTUAL_TOP = 0;
        VIRTUAL_BOTTOM = n*n+1;
        this.track[VIRTUAL_TOP] = true;
        this.track[VIRTUAL_BOTTOM] = true;
        percolationWQUF = new WeightedQuickUnionUF(n*n+2);
        fullCheckWQUF = new WeightedQuickUnionUF(n*n+2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row) || !isValid(col)) {
            throw new ArrayIndexOutOfBoundsException("Coordinate is invalid ("+row+", "+col+")");
        }
        final int openedIndex = encodeCoord(row, col);
        this.track[openedIndex] = true;
        this.counter++;

        // if it's first row, then union with Virtual Top (1,1), (1,2), ... (1,n)
        if (row == 1) {
            percolationWQUF.union(VIRTUAL_TOP, openedIndex);
            fullCheckWQUF.union(VIRTUAL_TOP, openedIndex);
        }
        // if it's last row, then union with Virtual Bottom
        if (row == N) {
            percolationWQUF.union(VIRTUAL_BOTTOM, openedIndex);
        }

        checkNeighborsAndUnionOpenOnes(openedIndex, row, col);
    }

    private void checkNeighborsAndUnionOpenOnes(int openedIndex, int row, int col) {
        // check and union left
        unionNeighbors(openedIndex, row, col - 1);
        // check and union right
        unionNeighbors(openedIndex, row, col + 1);
        // check and union right
        unionNeighbors(openedIndex, row - 1, col);
        // check and union right
        unionNeighbors(openedIndex, row + 1, col);
    }

    private void unionNeighbors(int openedIndex, int row, int col) {
        if (isValid(row) && isValid(col) && isOpen(row, col)) {
            percolationWQUF.union(openedIndex, encodeCoord(row, col));
            fullCheckWQUF.union(openedIndex, encodeCoord(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row) || !isValid(col)) {
            throw new ArrayIndexOutOfBoundsException("Coordinate is invalid ("+row+", "+col+")");
        }
        return this.track[encodeCoord(row, col)];
    }

    // is the site (row, col) full?

    public boolean isFull(int row, int col) {
        return fullCheckWQUF.find(VIRTUAL_TOP) == fullCheckWQUF.find(encodeCoord(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationWQUF.find(VIRTUAL_TOP) == percolationWQUF.find(VIRTUAL_BOTTOM);
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    private int encodeCoord(int row, int col) {
        //return ((N * row) - N) + col - 1;
        return (row - 1) * N + col;
    }

    private boolean isValid(int i) {
        return i >= 1 && i <= this.N;
    }
}
