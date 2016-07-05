/******************************************************************************
 * Author: Wilbur de Souza
 * Written: 2014-06-27
 *
 * compile : java Percolation.java
 * requires: algs4.jar
 * Data type to model physical percolation .
 ******************************************************************************/
public class Percolation {
    
    private boolean[] sites;   //linear array to store the grid cells/sites
    private int virtualTop;    //virtual site for efficiency
    private int virtualBottom; //virtual site for efficiency
    private WeightedQuickUnionUF percolationGraph;  //weighted QU find algorithm
    private WeightedQuickUnionUF fullGraph; //required to avoid backwash bug
    private int gridSize;
    
    /*create N-by-N grid, with all sites blocked 
     * throws IllegalArgumentException
     * */
    public Percolation(int N) {
        if (N <= 0 || (N >= 0xffff)) {
            throw new IllegalArgumentException();
        }
        gridSize = N;
        sites = new boolean[N*N]; 
        //add 2 nodes for virtual sites
        virtualTop = N*N;
        virtualBottom = N*N+1;
        percolationGraph = new WeightedQuickUnionUF(N*N +2); 
        fullGraph = new WeightedQuickUnionUF(N*N +2);  
    }
     
    /* open site and connect with open neighbors
     * throws IndexOutOfBoundsException
     */
    public void open(int i, int j) {
        if (isOpen(i, j))
            return;
        int idx = index(i, j);
        sites[idx] = true;
        
      //connect virtual sites to site
        if (i == 1) {
            percolationGraph.union(virtualTop, idx); 
            fullGraph.union(virtualTop, idx); 
        }   
        if (i == gridSize) {
            percolationGraph.union(virtualBottom, idx); 
        }
    
        //connect with left if open
        if (j > 1  && isOpen(i, j-1)) {
            percolationGraph.union(index(i, j-1), idx);
            fullGraph.union(index(i, j-1), idx);
        }
        
      //connect with top if open
        if (i > 1  && isOpen(i-1, j)) {
            percolationGraph.union(index(i-1, j), idx);
            fullGraph.union(index(i-1, j), idx);
        }
        
      //connect with right if open
        if (j < gridSize  && isOpen(i, j+1)) {
            percolationGraph.union(index(i, j+1), idx);
            fullGraph.union(index(i, j+1), idx);
        }
        
      //connect with bottom if open
        if (i < gridSize  && isOpen(i+1, j)) {
            percolationGraph.union(index(i+1, j), idx);
            fullGraph.union(index(i+1, j), idx);
        }
    }
    
    /* check if site open */
    public boolean isOpen(int i, int j) {
        return sites[index(i, j)];
    }
    
    /* check if site is full i.e. water can percolate to site */
    public boolean isFull(int i, int j) {
        return fullGraph.connected(virtualTop, index(i, j));
    }
    
    /* does water percolate to bottom */
    public boolean percolates() {
        return percolationGraph.connected(virtualTop, virtualBottom);
    }
    
    /* Convert grid coordinates (x, y) to an array index*/
    private int index(int r, int c) {
        if (r <= 0 || r > gridSize || c <= 0 || c > gridSize)
            throw new IndexOutOfBoundsException(r + "," + c + " is out of bounds"
                    + " for grid of " + gridSize + " x " + gridSize);
        return (r - 1) * gridSize + (c - 1);
    }
}