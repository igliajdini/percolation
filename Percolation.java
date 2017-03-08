import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UF2;
    private int sink;
    private int source = 0;
    private boolean[][] open;
    private int size;
    private int openSites; // to keep track of the open sites
    private int N;
    private int result;

    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        this.N = N;
        this.UF = new WeightedQuickUnionUF(N * N + 2);
        this.UF2 = new WeightedQuickUnionUF(N * N + 1);
        this.openSites = 0;
        this.size = N;
        this.open = new boolean[N][N];
        this.sink = (N * N + 1);
        this.source = 0;
        this.result = result;
        for (int i = 0; i <= N; i++) {
            UF.union(source, encode(0, i));
            UF2.union(source, encode(0, i));
        }
        for (int j = 0; j <= N; j++) {
            UF.union(sink, encode(N-1, j));
        }
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {
        if (!isOpen(i, j)) {

            open[i][j] = true;
            openSites++;
            if ((j-1) >= 0) {
                if (isOpen(i, (j-1))) {
                    UF.union(encode(i, j), encode(i, (j-1)));
                    UF2.union(encode(i, j), encode(i, (j-1)));
                }
            }
            if ((j+1) < N) {
                if (isOpen(i, (j+1))) {
                    UF.union(encode(i, j), encode(i, (j+1)));
                    UF2.union(encode(i, j), encode(i, (j+1)));
                }
            }
            if ((i-1) >= 0) {
                if (isOpen((i-1), j)) {
                    UF.union(encode(i, j), encode((i-1), j));
                    UF2.union(encode(i, j), encode((i-1), j));
                }
            }
            if ((i+1) < N) {
                if (isOpen((i+1), j)) { 
                    UF.union(encode(i, j), encode((i+1), j));
                    UF2.union(encode(i, j), encode((i+1), j));
                }     
            }
        }
    }    

    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
        if (open[i][j]) {
            return true;
        }
        return false;

    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        if (open[i][j]) {
            if (UF2.connected(encode(i, j), source)) {
                return true;
            }
        }
        return false;
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        if (UF.connected(source, sink)) {
            return true;
        }
        return false;

    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return i * N + j + 1;

    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename); 
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}