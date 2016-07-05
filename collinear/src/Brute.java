/*************************************************************************
 * Name: Wilbur de Souza
 * 
 * Compilation:  javac Brute.java
 * Execution:    java Brute points.txt
 * Dependencies: StdDraw.java, Point.java
 *
 * Write a program Brute.java that examines 4 points at a time and checks
 *  whether they all lie on the same line segment, printing out any such
 *   line segments to standard output and drawing them using standard 
 *   drawing. To check whether the 4 points p, q, r, and s are collinear,
 *    check whether the slopes between p and q, between p and r, 
 *    and between p and s are all equal.
 *************************************************************************/
import java.util.Arrays;

public class Brute {

    private static void init() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
    }

    public static void main(String[] args) {
        init();
        if (args.length != 1)
            throw new IllegalArgumentException("No input file.");
        Point[] points = readInput(args[0]);
        solve(points);
        StdDraw.show(0);
    }

    /**
     * Read input files of point coordinates
     * 
     * Return an array of Point objects.
     */
    private static Point[] readInput(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }
        return points;
    }

    private static void solve(Point[] points) {
        int N = points.length;
        Arrays.sort(points);
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++)
                    for (int l = k + 1; l < N; l++)
                        if (points[i].slopeTo(points[j]) == points[i]
                                .slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i]
                                        .slopeTo(points[l])) {
                            StdOut.println(points[i].toString() + " -> "
                                    + points[j].toString() + " -> "
                                    + points[k].toString() + " -> "
                                    + points[l].toString());
                            points[i].drawTo(points[l]);
                        }
    }
}