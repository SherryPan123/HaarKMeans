package cn.edu.fudan.haarkmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class TestIKMeans {

    public static void main(String[] args) {
        List<DoublePoint> dataset = FileRead.readFromFile("data/CBF_TRAIN");
        List<List<DoublePoint>> haarDataset = HaarWavelet.getHaarWavelet(dataset);
        int kSize = 3;
        List<DoublePoint> centers = new ArrayList<>();
        int[] preClusterInfo = new int[dataset.size()];
        for (int i=0; i<haarDataset.size(); i++) {
            KMeans kMeans = new KMeans();
            List<DoublePoint> datasetLevel = haarDataset.get(i);
            if (i == 0) {
                centers = kMeans.compute(kSize, datasetLevel, null);
            } else {
                List<DoublePoint> initCenters = projectCenters(centers);
                centers = kMeans.compute(kSize, datasetLevel, initCenters);
            }
            int[] clusterInfo = kMeans.getClusters(datasetLevel, centers);
            if (i > 0 && isUnchanged(clusterInfo, preClusterInfo)) {
                System.out.println("Converged!");
                for (int info : clusterInfo) {
                    System.out.print(info + ",");
                }
                System.out.println();
                break;
            }
            preClusterInfo = clusterInfo;
        }
    }

    private static boolean isUnchanged(int[] clusterInfo, int[] preClusterInfo) {
        if (clusterInfo.length != preClusterInfo.length) return false;
        for(int i=0; i<clusterInfo.length; i++) {
            if (clusterInfo[i] != preClusterInfo[i]) return false;
        }
        return true;
    }

    private static List<DoublePoint> projectCenters(List<DoublePoint> points) {
        List<DoublePoint> points2 = new ArrayList<>();
        for (DoublePoint point : points) {
            double[] point2 = new double[point.getLength()*2];
            for(int i=0; i<point.getLength(); i++) {
                point2[i<<1] = point2[i<<1|1] = point.getPoints()[i];
            }
            points2.add(new DoublePoint(point2));
        }
        return points2;
    }

}
