/*************************************************************************
 * Name: Wilbur de Souza
 *
 * Compilation:  javac Board.java
 * Execution:    java Board puzzle04.txt
 *
 * Description: 8Puzzle Board implementation
 *
 *************************************************************************/
import java.util.Arrays;

public class Board {

    private int N;
    private int[][] tiles;

    // construct a blocks from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] bl) {
        N = bl[0].length;
        tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            tiles[i] = Arrays.copyOf(bl[i], N);
    }

    // blocks dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        int seed = 1;
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++)
                if (tiles[x][y] != seed++)
                    count++;
        // Subtract 1 for the empty block.
        return count - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manDist = 0;
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++) {
                int value = tiles[x][y];
                if (value != 0) {
                    int targetX = (value - 1) / N; // expected x-coordinate
                    int targetY = (value - 1) % N; // expected y-coordinate
                    int dx = x - targetX; // x-distance to expected coordinate
                    int dy = y - targetY; // y-distance to expected coordinate
                    manDist += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manDist;
    }

    // is this blocks the goal blocks?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a blocks obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        if (N < 2) // nothing to exchange
            return null;

        Board twin = new Board(tiles);
        if (tiles[0][0] != 0 && tiles[0][1] != 0)
            twin.exch(0, 0, 0, 1);
        else
            twin.exch(N - 1, N - 1, N - 1, N - 2);
        return twin;
    }

    // does this blocks equal y?
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that == this)
            return true;
        if (that.getClass() != this.getClass())
            return false;

        Board tBoard = (Board) that;
        for (int x = 0; x < N; x++)
            if (!Arrays.equals(tiles[x], tBoard.tiles[x]))
                return false;
        return true;
    }

    private void exch(int x1, int y1, int x2, int y2) {
        int temp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = temp;
    }

    // all neighboring blocks
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        // Find zero
        int x = 0;
        int y = 0;
        findzero: for (x = 0; x < N; x++)
            for (y = 0; y < N; y++)
                if (tiles[x][y] == 0)
                    break findzero;

        Board neigh;
        if (x > 0) {
            neigh = new Board(tiles);
            neigh.exch(x, y, x - 1, y);
            q.enqueue(neigh);
        }
        if (x < N - 1) {
            neigh = new Board(tiles);
            neigh.exch(x, y, x + 1, y);
            q.enqueue(neigh);
        }
        if (y > 0) {
            neigh = new Board(tiles);
            neigh.exch(x, y, x, y - 1);
            q.enqueue(neigh);
        }
        if (y < N - 1) {
            neigh = new Board(tiles);
            neigh.exch(x, y, x, y + 1);
            q.enqueue(neigh);
        }
        return q;
    }

    // string representation of the blocks
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                s.append(String.format("%2d ", tiles[x][y]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        StdOut.print(initial.toString());
        StdOut.print(initial.twin().toString());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.dimension());
        StdOut.println(initial.isGoal());

        for (Board b : initial.neighbors()) {
            StdOut.println(b.toString());
        }
    }
}