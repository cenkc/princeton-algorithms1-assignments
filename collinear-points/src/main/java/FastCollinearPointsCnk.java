package main.java;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

/**
 * created by cenkc on 6/27/2020
 */
public class FastCollinearPointsCnk {

    // finds all line segments containing 4 or more points
    public FastCollinearPointsCnk(Point[] points) {
        final int length = points.length;

        validateAndSortPoints(points, length);
    }

    // the number of line segments
    public int numberOfSegments() {
        throw new NotImplementedException();
    }

    // the line segments
    public LineSegment[] segments() {
        throw new NotImplementedException();
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
}
