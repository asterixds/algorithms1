/******************************************************************************
 * Author: Wilbur de Souza
 * Written: 2014-06-27
 *
 * compile : java PercolationStats.java
 * test:   : javac PercolationStats.java
 * run     : java PercolationStats 20 200
 * requires: stdlib.jar
 * Data type to run percolation experiments
 ******************************************************************************/
public class PercolationStats 
{
    private static final double C95 = 1.96;  //constant for 95% confidence
    private double[] thresholds; // container to store experiment results
    private double mean;        // mean of thresholds from experiments
    private double stddev;      //standard deviation of thresholds from experiments
    
    /*Conduct experiments*/
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be > 0");
        }
        
        thresholds = new double[T];
        int i = 0, j = 0, n = 0;
        for (int t = 0; t < T; t++)
        {
            Percolation p = new Percolation(N);
            n = 0;
            while (!p.percolates())
            {
                do {
                    i = StdRandom.uniform(1, N + 1);
                    j = StdRandom.uniform(1, N + 1);
                } while(p.isOpen(i, j));
                p.open(i, j);
                n++;
            }
            thresholds[t] = (double) n / (N*N);
            mean = StdStats.mean(thresholds);
            stddev = StdStats.stddev(thresholds);
        }
    }

    /* return the mean of percolation thresholds from the experiments */
    public double mean()
    {
        return mean;
    }

    /* return the stand deviation of the threshold of experiments */
    public double stddev()
    {
        return stddev;
    }
    
    /* return the lower 95% confidence thresholds*/
    public double confidenceLo() {
        return mean - C95 * stddev / Math.sqrt(thresholds.length); 
    }
    
    /* return the lower 95% confidence thresholds*/
    public double confidenceHi() {
        return mean + C95 * stddev / Math.sqrt(thresholds.length); 
    }
    
    public static void main(String[] args) {
        int N;
        int T;
  
        if (args.length == 0) {
            N = 20;
            T = 20;
        }
        else {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        
        PercolationStats pstats = new PercolationStats(N, T);
        System.out.println("mean                    = " + pstats.mean());
        System.out.println("stddev                  = " + pstats.stddev());
        System.out.println("95% confidence interval = " 
                + pstats.confidenceLo() + ", " + pstats.confidenceHi());
    }
}