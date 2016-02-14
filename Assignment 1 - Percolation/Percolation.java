import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int size;
   private WeightedQuickUnionUF nodes, nodes2;
   private boolean[] openness;
   
   public Percolation(int N) {
       if (N <= 0) throw new IllegalArgumentException("Illegal argument");
       size = N;
       nodes = new WeightedQuickUnionUF(size*size+2);
       nodes2 = new WeightedQuickUnionUF(size*size+1);
       openness = new boolean[size*size+2];
       for (int i = 0; i < size; i++) {
           for (int j = 0; j < size; j++) {
               openness[i*size+j] = false;
           }
       }
       openness[size*size] = true;
       openness[size*size+1] = true;
   }
   
   private int xyTo1D(int i, int j) {
       return (int) (size*(i-1)+j-1);
   }
   
   public void open(int i, int j) {
       if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > size) throw new IndexOutOfBoundsException("row index j out of bounds");
       if (openness[xyTo1D(i, j)]) return;
       openness[xyTo1D(i, j)] = true;
       
       if (i == 1) {
           nodes.union(size*size, xyTo1D(i, j));
           nodes2.union(size*size, xyTo1D(i, j));
       }
       if (i == size) nodes.union(size*size+1, xyTo1D(i, j));
       if (i > 1) {
           if (openness[xyTo1D(i-1, j)]) { //check the connection with the top
               nodes.union(xyTo1D(i, j), xyTo1D(i-1, j));
               nodes2.union(xyTo1D(i, j), xyTo1D(i-1, j)); 
           }
       }
       if (i < size) {
           if (openness[xyTo1D(i+1, j)]) { //check the connection with the bottom
               nodes.union(xyTo1D(i, j), xyTo1D(i+1, j)); 
               nodes2.union(xyTo1D(i, j), xyTo1D(i+1, j));
           }
       }
       if (j > 1) {
           if (openness[xyTo1D(i, j-1)]) { //check the connection with the left
               nodes.union(xyTo1D(i, j), xyTo1D(i, j-1)); 
               nodes2.union(xyTo1D(i, j), xyTo1D(i, j-1));
           }
       }
       if (j < size) {
           if (openness[xyTo1D(i, j+1)]) { //check the connection with the right
               nodes.union(xyTo1D(i, j), xyTo1D(i, j+1)); 
               nodes2.union(xyTo1D(i, j), xyTo1D(i, j+1));
           }
       }
   }
   
   public boolean isOpen(int i, int j) {
       if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > size) throw new IndexOutOfBoundsException("row index j out of bounds");
       return openness[xyTo1D(i, j)];
   }
   
   public boolean isFull(int i, int j) {
       if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > size) throw new IndexOutOfBoundsException("row index j out of bounds");
       return openness[xyTo1D(i, j)] && nodes2.connected(xyTo1D(i, j), size*size);
   }
   
   public boolean percolates() {
       return nodes.connected(size*size, size*size+1);
   }
}