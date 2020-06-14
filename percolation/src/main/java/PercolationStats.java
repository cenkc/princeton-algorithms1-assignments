package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * created by cenkc on 5/17/2020
 */
public class PercolationStats {

    private int size;
    private int experimentCount;
    private double[] openSitesRatio;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size (n) must be greater than zero");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("Experiments count (trials) must be greater than zero");
        }
        experimentCount = trials;
        size = n;
        openSitesRatio = new double[experimentCount];

        for (int i = 0; i < experimentCount; i++) {
            Percolation percolation = new Percolation(n);
            int openSitesCounter = 0;
            while ( ! percolation.percolates()) {
                final int random1 = StdRandom.uniform(1, size + 1);
                final int random2 = StdRandom.uniform(1, size + 1);
                if ( ! percolation.isOpen(random1, random2)) {
                    percolation.open(random1, random2);
                    openSitesCounter++;
                }
            }
            openSitesRatio[i] = ((double) openSitesCounter) / (size * size);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSitesRatio);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSitesRatio);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(experimentCount)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * stddev())/ Math.sqrt(experimentCount)));
    }

    // test client (see below)
    public static void main(String[] args) {
//        int n = Integer.parseInt(args[0]);
//        int trials = Integer.parseInt(args[1]);
//        PercolationStats ps = new PercolationStats(n,trials);
        PercolationStats ps = new PercolationStats(200,100);
        StdOut.println("        Mean = " + ps.mean());
        StdOut.println("     Std Dev = " + ps.stddev());
        StdOut.println("ConfidenceLo = " + ps.confidenceLo());
        StdOut.println("ConfidenceHi = " + ps.confidenceHi());
    }
}
