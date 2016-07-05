import java.util.Comparator;

public class Solver {

    private SearchNode goal;

    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;

        public SearchNode(Board b) {
            board = b;
            moves = 0;
            previous = null;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        PriorityOrder po = new PriorityOrder();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(po);
        SearchNode sn = new SearchNode(initial);
        pq.insert(sn);

        /* twin search node */
        PriorityOrder twinPo = new PriorityOrder();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(twinPo);
        SearchNode twinSn = new SearchNode(initial.twin());
        twinPq.insert(twinSn);
        SearchNode minNode = null;
        SearchNode twinMinNode = null;

        /* delete the minimum priority node from queue until goal is reached */
        while (true) {
            minNode = step(pq);
            if (minNode.board.isGoal()) {
                goal = minNode;
                break;
            }
            if (step(twinPq).board.isGoal()) {
                goal = null;
                break;
            }
        }
    }

    private SearchNode step(MinPQ<SearchNode> pq) {
        SearchNode least = pq.delMin();
        for (Board b : least.board.neighbors()) {
            if ((least.previous == null) || !b.equals(least.previous.board)) {
                SearchNode node = new SearchNode(b);
                node.moves = least.moves + 1;
                node.previous = least;
                pq.insert(node);
            }
        }
        return least;
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode s1, SearchNode s2) {
            int priority1 = s1.board.manhattan() + s1.moves;
            int priority2 = s2.board.manhattan() + s2.moves;

            if (priority1 > priority2)
                return 1;
            else if (priority1 < priority2)
                return -1;
            else
                return 0;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (goal == null)
            return -1;
        return goal.moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> bStack = new Stack<Board>();
        for (SearchNode s = goal; s != null; s = s.previous)
            bStack.push(s.board);

        return bStack;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}