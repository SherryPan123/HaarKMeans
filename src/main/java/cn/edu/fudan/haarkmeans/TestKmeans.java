package cn.edu.fudan.haarkmeans;

import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class TestKmeans {

    public static void main(String[] args) {
        KMeans kMeans = new KMeans();
        List<DoublePoint> dataset = FileRead.readFromFile("data/CBF_TRAIN");
        List<DoublePoint> centers = kMeans.compute(3, dataset, null);
        kMeans.getClusters(dataset, centers);
    }

}
