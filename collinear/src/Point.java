/*************************************************************************
 * Name: Wilbur de Souza
 *
 * Compilation:  javac Point.java
 * Execution:    java Point
 * Dependencies: StdDraw.java
 *
 * Description: immutable data type Point that represents a point in the 
 * plane
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new SlopeOrderComparator();
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (that.y == this.y)
            return 0.0;
        return ((double) that.y - this.y) / (that.x - this.x);
    }

    // is this point smaller than that ?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y == that.y)
            return this.x - that.x;
        return this.y - that.y;
    }

    private class SlopeOrderComparator implements Comparator<Point> {
        public int compare(Point a, Point c) {
            double r = slopeTo(a) - slopeTo(c);
            if (r < 0.0)
                return -1;
            else if (r > 0.0)
                return 1;
            else
                return 0;
        }
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
    }
}