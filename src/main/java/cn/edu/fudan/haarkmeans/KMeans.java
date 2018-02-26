package cn.edu.fudan.haarkmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class KMeans {

    public List<DoublePoint> compute(int kSize, List<DoublePoint> dataset, List<DoublePoint> initCenters) {
        double lb = Double.MAX_VALUE;
        double ub = -Double.MAX_VALUE;
        for (int i=0; i<dataset.size(); i++){
            double[] p = dataset.get(i).getPoints();
            for(int j=0; j<p.length; j++) {
                lb = Math.min(lb, p[j]);
                ub = Math.max(ub, p[j]);
            }
        }
        int len = dataset.get(0).getLength();
        // init kSize centroids
        List<DoublePoint> centers = initCenters;
        if (initCenters == null) {
            // random init kSize centroids
            centers = initializeRandomCenters(kSize, len, lb, ub);
        }
        double[] randomCenter = getRandomCenter(len, lb, ub);
        // for each centroid, find nearest
        boolean converged;
        int iter = 0;
        do {
            List<DoublePoint> newCenters = getNewCenters(dataset, centers, randomCenter);
            double dist = getDistance(centers, newCenters);
            centers = newCenters;
            converged = (dist == 0);
            iter++;
        } while ((!converged) && (iter < 100));
        return centers;
    }

    private double[] getRandomCenter(int length, double lowerBound, double upperBound) {
        double[] x = new double[length];
        for (int j=0; j<length; j++) {
            x[j] = Math.random() * (upperBound - lowerBound) + lowerBound;
        }
        return x;
    }

    public List<DoublePoint> initializeRandomCenters(int n, int length, double lowerBound, double upperBound) {
        List<DoublePoint> centers = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            double[] x = getRandomCenter(length, lowerBound, upperBound);
            centers.add(new DoublePoint(x));
        }
        return centers;
    }

    /*public List<List<DoublePoint>> getClusters(List<DoublePoint> dataset, List<DoublePoint> centers) {
        List<List<DoublePoint>> clusters = new ArrayList<>();
        for (int i = 0; i < centers.size(); i++) {
            clusters.add(new ArrayList<>());
        }
        for (DoublePoint data : dataset) {
            int index = data.getNearestPointIndex(centers);
            clusters.get(index).add(data);
        }
        return clusters;
    }*/

    public int[] getClusters(List<DoublePoint> dataset, List<DoublePoint> centers) {
        int[] clusterInfo = new int[dataset.size()];
        for (int i=0; i<dataset.size(); i++) {
            int index = dataset.get(i).getNearestPointIndex(centers);
            clusterInfo[i] = index + 1;
        }
        return clusterInfo;
    }

    private List<DoublePoint> getNewCenters(List<DoublePoint> dataset, List<DoublePoint> centers, double[] randomCenter) {
        List<List<DoublePoint>> clusters = new ArrayList<>();
        for (int i = 0; i < centers.size(); i++) {
            clusters.add(new ArrayList<DoublePoint>());
        }
        for (DoublePoint data : dataset) {
            int index = data.getNearestPointIndex(centers);
            clusters.get(index).add(data);
        }
        List<DoublePoint> newCenters = new ArrayList<>(centers.size());
        for (List<DoublePoint> cluster : clusters) {
            if (cluster.size() == 0) newCenters.add(new DoublePoint(randomCenter));
            else newCenters.add(DoublePoint.getMean(cluster));
        }
        return newCenters;
    }

    private double getDistance(List<DoublePoint> oldCenters, List<DoublePoint> newCenters) {
        double accumDist = 0;
        for (int i = 0; i < oldCenters.size(); i++) {
            if (oldCenters.get(i) == null) {
                System.out.println("EEEEEEE");
            }
            double dist = oldCenters.get(i).getDistance(newCenters.get(i));
            accumDist += dist;
        }
        return accumDist;
    }

}
