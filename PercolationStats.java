import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private int T;
    private double[] p;
    private int N;
    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        //throw illegal arg exeption
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        if (T <= 0) {
            throw new IllegalArgumentException();
        }
        
        this.N = N;
        this.T = T;
        p = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation newPerc = new Percolation(N);
            while (!newPerc.percolates()) {
                int a = StdRandom.uniform(0, N);
                int b = StdRandom.uniform(0, N);
                if (!newPerc.isOpen(a, b)) {
                    newPerc.open(a, b);
                }
            }
            //double value =1.0 * ( NewPerc.numberOfOpenSites() / (N*N) ) ;
           double value = 1.0 * newPerc.numberOfOpenSites() / (N*N);
            p[i] = value;
        }

    }

    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(p);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(p);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double conLow = mean() - ((1.96 * stddev()) / Math.sqrt(T));
        return conLow;
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        double conHigh = mean() + ((1.96 * stddev()) / Math.sqrt(T));
        return conHigh;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
