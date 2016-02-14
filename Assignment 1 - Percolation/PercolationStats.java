import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int size;
    
    public PercolationStats(int N, int T) {
        results = new double[T];
        if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        size = T;
        for (int i = 0; i < T; i++) {
            int count = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int a = 1 + StdRandom.uniform(N);
                int b = 1 + StdRandom.uniform(N);
                while (perc.isOpen(a,b)) {
                    a = 1 + StdRandom.uniform(N);
                    b = 1 + StdRandom.uniform(N);
                }
                perc.open(a, b);
                count++;
            }
            results[i] = 1.0 * count / (N*N);
        }
            
    }// perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(results);
    }// sample mean of percolation threshold
    public double stddev() {                    // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }
    public double confidenceLo() {              // low  endpoint of 95% confidence interval
        return (double) (mean()-1.96*stddev()/(double)(Math.sqrt(size)));
    }
    public double confidenceHi() {              // high endpoint of 95% confidence interval
        return (double) (mean()+1.96*stddev()/(double)(Math.sqrt(size)));
    }
    public static void main(String[] args) {    // test client (described below)
        int int1 = Integer.parseInt(args[0]);
        int int2 = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(int1, int2);
        StdOut.printf("%-23s", "mean ");
        StdOut.print(" = ");
        StdOut.print(perc.mean());
        StdOut.println();
        StdOut.printf("%-23s", "stddev ");
        StdOut.print(" = ");
        StdOut.print(perc.stddev());
        StdOut.println();
        StdOut.printf("%-23s", "95% confidence interval");
        StdOut.print(" = ");
        StdOut.print(perc.confidenceLo());
        StdOut.print(", ");
        StdOut.println(perc.confidenceHi());
    }
}