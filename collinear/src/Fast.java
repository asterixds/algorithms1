/*************************************************************************
 * Name: Wilbur de Souza
 * 
 * Compilation:  javac Fast.java
 * Execution:    java Fast input8.txt
 * Dependencies: StdDraw.java, Point.java
 *
 * Write a program Fast.java that eGiven a point p, the following method
 * determines whether p participates in a set of 4 or more collinear 
 * points.Think of p as the origin. For each other point q, determine 
 * the slope it makes with p. Sort the points according to the slopes
 * they makes with p. Check if any 3 (or more) adjacent points in the
 * sorted order have equal slopes with respect to p. If so, these 
 * points, together with p, are collinear.
 *************************************************************************/

import java.util.Arrays;

public class Fast {

    private static final int PT_THRESHOLD = 3;

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
     * @param filename
     * @return an array of Point objects.
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

    /**
     * Print to stdout a textual representation of a line segment (10000, 0) ->
     * (7000, 3000) -> (3000, 7000) -> (0, 10000)
     * 
     * Draw line segments connecting a SORTED array of points points is a SORTED
     * array of Point objects
     */

    private static void output(Point origin, Point[] seq, int start, int stop) {
        origin.drawTo(seq[stop - 1]);
        String lineStr = origin.toString() + " -> ";
        for (int l = start; l < stop - 1; l++)
            lineStr += (seq[l].toString() + " -> ");
        lineStr += seq[stop - 1].toString();
        StdOut.println(lineStr);
    }

    private static void solve(Point[] a) {
        int N = a.length;
        /* first sort the array by coordinates */
        Arrays.sort(a);

        Point[] scratch = new Point[N];
        for (int i = 0; i < N - 3; i++) {
            for (int j = i; j < N; j++)
                scratch[j] = a[j];

            Arrays.sort(scratch, i + 1, N, scratch[i].SLOPE_ORDER);
            Arrays.sort(scratch, 0, i, scratch[i].SLOPE_ORDER);

            int start = i + 1;
            int stop = i + 2;
            int pHead = 0;
            while (stop < N) {
                /* start a run */
                double headSlope = scratch[i].slopeTo(scratch[start]);
                while (stop < N
                        && headSlope == scratch[i].slopeTo(scratch[stop]))
                    stop++;
                /* check if we have a segment */
                if (stop - start >= 3) {
                    // * strip out duplicates */
                    double pSlope = Double.NEGATIVE_INFINITY;
                    while (pHead < i) {
                        pSlope = scratch[i].slopeTo(scratch[pHead]);
                        if (pSlope < headSlope)
                            pHead++;
                        else
                            break;
                    }
                    if (pSlope != headSlope) {
                        output(scratch[i], scratch, start, stop);
                    }
                }
                /* continue run */
                start = stop;
                stop = stop + 1;
            }
        }

    }
}
