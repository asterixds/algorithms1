
public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV rect) {
            RectHV r = rect;
            if (r == null)
                r = new RectHV(0, 0, 1, 1);
            this.rect = r;
            this.p = p;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(p.toString());
            s.append(" ");
            s.append(rect.toString());
            if (left != null) {
                s.append("\nlb: ");
                s.append(left.toString());
            }
            if (right != null) {
                s.append("\nrt: ");
                s.append(right.toString());
            }
            return s.toString();
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }

    private Node insert(Node x, Point2D p, boolean orientation, double xmin,
            double ymin, double xmax, double ymax) {
        if (x == null) {
            this.size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        if (x.p.equals(p)) {
            return x;
        }

        if (orientation == VERTICAL) {
            double cmp = p.x() - x.p.x();

            if (cmp < 0) {
                x.left = insert(x.left, p, !orientation, x.rect.xmin(),
                        x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                x.right = insert(x.right, p, !orientation, x.p.x(),
                        x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
            }
        } else {
            double cmp = p.y() - x.p.y();

            if (cmp < 0) {
                x.left = insert(x.left, p, !orientation, x.rect.xmin(),
                        x.rect.ymin(), x.rect.xmax(), x.p.y());
            } else {
                x.right = insert(x.right, p, !orientation, x.rect.xmin(),
                        x.p.y(), x.rect.xmax(), x.rect.ymax());
            }
        }

        return x;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            return false;
        }

        if (x.p.equals(p)) {
            return true;
        }

        double cmp;
        if (orientation == VERTICAL) {
            cmp = p.x() - x.p.x();
        } else {
            cmp = p.y() - x.p.y();
        }

        if (cmp < 0) {
            return contains(x.left, p, !orientation);
        } else {
            return contains(x.right, p, !orientation);
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node x, boolean orientation) {
        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        if (x.left != null) {
            draw(x.left, !orientation);
        }

        if (x.right != null) {
            draw(x.right, !orientation);
        }

        // draw point last to be on top of line
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        if (x == null) {
            return;
        }

        if (!x.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }

        range(x.left, rect, q);

        range(x.right, rect, q);
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        return nearest(root, p, root.p, true);
    }

    // Find the nearest point that is closer than distance
    private Point2D nearest(Node x, Point2D p, Point2D mp, boolean orientation) {
        Point2D min = mp;

        if (x == null)
            return min;
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min))
            min = x.p;

        // choose the side that contains the query point first
        if (orientation == VERTICAL) {
            if (x.p.x() < p.x()) {
                min = nearest(x.right, p, min, !orientation);
                if (x.left != null
                        && (min.distanceSquaredTo(p) > x.left.rect
                                .distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, !orientation);
            } else {
                min = nearest(x.left, p, min, !orientation);
                if (x.right != null
                        && (min.distanceSquaredTo(p) > x.right.rect
                                .distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, !orientation);
            }
        } else {
            if (x.p.y() < p.y()) {
                min = nearest(x.right, p, min, !orientation);
                if (x.left != null
                        && (min.distanceSquaredTo(p) > x.left.rect
                                .distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, !orientation);
            } else {
                min = nearest(x.left, p, min, !orientation);
                if (x.right != null
                        && (min.distanceSquaredTo(p) > x.right.rect
                                .distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, !orientation);
            }
        }
        return min;
    }

    public static void main(String[] args) {
    }
}