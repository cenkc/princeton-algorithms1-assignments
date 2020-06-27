package main.java;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by cenkc on 6/28/2020
 */
public class BruteCollinearPointsCnk {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPointsCnk(Point[] points) {

        final int length = points.length;

        validateAndSortPoints(points, length);

        List<LineSegment> lineSegmentList = new ArrayList<>();

        for (int p = 0; p < length - 3; p++) {
            for (int q = p + 1; q < length - 2; q++) {
                for (int r = q + 1; r < length - 1; r++) {
                    final double slopePQ = points[p].slopeTo(points[q]);
                    final double slopeQR = points[q].slopeTo(points[r]);
                    if (slopePQ != slopeQR) {
                        continue;
                    }
                    for (int s = r + 1; s < length; s++) {
                        final double slopeRS = points[r].slopeTo(points[s]);
                        if (slopePQ == slopeRS) {
                            lineSegmentList.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }

        final int size = lineSegmentList.size();
        lineSegments = lineSegmentList.toArray(new LineSegment[size]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    private void validateAndSortPoints(Point[] points, int length) {
        if (points == null) {
            throw new IllegalArgumentException("points array is null");
        }

        for (int i = 0; i < length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException(i + ". element of the points array is null..");
            }
        }

        Arrays.sort(points);

        for (int i = 0; i < length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate entry in points array");
            }
        }
    }

    // same as FastCollinearPoints' main method (it's given in the specification)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPointsCnk collinear = new BruteCollinearPointsCnk(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
