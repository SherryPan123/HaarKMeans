package cn.edu.fudan.haarkmeans;

import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class DoublePoint {

    private double[] points;

    public DoublePoint(int size) {
        points = new double[size];
    }

    public DoublePoint(double[] points) {
        this.points = points;
    }

    public static DoublePoint getMean(List<DoublePoint> allPoints) {
        if (allPoints.size() == 0) return null;
        int len = allPoints.get(0).getLength();
        double[] accum = new double[len];
        double[] mean = new double[len];
        if (allPoints.size() == 0) return new DoublePoint(mean);
        for (DoublePoint p : allPoints) {
            for (int i=0; i<p.getLength(); i++) {
                accum[i] += p.getPoints()[i];
            }
        }
        for (int i=0; i<len; i++) {
            mean[i] = accum[i] / allPoints.size();
        }
        return new DoublePoint(mean);
    }

    public double getDistance(DoublePoint doublePoint) {
        if (doublePoint == null) return Double.MAX_VALUE;
        double[] other = doublePoint.getPoints();
        if (other.length != points.length) {
            System.out.println("Error: Length not match.");
            return -1.0;
        }
        double sum = 0d;
        for (int i=0; i<other.length; i++) {
            sum += Math.pow(points[i] - other[i], 2);
        }
        return Math.sqrt(sum);
    }

    public int getNearestPointIndex(List<DoublePoint> centers) {
        int index = -1;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < centers.size(); i++) {
            double dist = this.getDistance(centers.get(i));
            if (dist < minDist) {
                minDist = dist;
                index = i;
            }
        }
        return index;
    }

    public int getLength() {
        return points.length;
    }

    public double[] getPoints() {
        return points;
    }
}
