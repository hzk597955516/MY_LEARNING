package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int N;
    private int T;
    private double[] x;
    private Percolation testmodel;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;
        this.T = T;
        x = new double[T];

        for (int i = 0; i < T; i++){
            testmodel = pf.make(N);
            while (!testmodel.percolates()){
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                testmodel.open(x, y);
            }
            x[i] = (double)testmodel.numberOfOpenSites() / (N * N);
        }
    }

    public double mean(){
        double sum = 0;
        for (int i = 0; i < T; i++){
            sum += x[i];
        }
        return sum / T;
    }

    public double stddev(){
        double sum = 0;
        double samplemean = mean();
        for (int i = 0; i < T; i++){
            sum += Math.pow(x[i] - samplemean, 2);
        }
        return Math.sqrt(sum / (T - 1));
    }

    public double confidenceLow(){
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    public double confidenceHigh(){
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

    public static void main(String[] args){
        PercolationStats a = new PercolationStats(50, 10000, new PercolationFactory());
        System.out.println(a.confidenceLow());
        System.out.println(a.mean());
        System.out.println(a.confidenceHigh());
    }
}
