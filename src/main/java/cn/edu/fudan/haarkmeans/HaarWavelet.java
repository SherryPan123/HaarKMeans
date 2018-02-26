package cn.edu.fudan.haarkmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class HaarWavelet {

    private static double w0 = 0.7071067812;
    private static double w1 = -0.7071067812;
    private static double s0 = 0.7071067812;
    private static double s1 = 0.7071067812;

    public static double[] FWT(double[] data) {
        double[] temp = new double[data.length];

        int h = data.length >> 1;
        double[] ans = new double[h];
        for (int i = 0; i < h; i++) {
            int k = (i << 1);
            temp[i] = data[k] * s0 + data[k + 1] * s1;
            temp[i + h] = data[k] * w0 + data[k + 1] * w1;
        }

        for (int i = 0; i < h; i++)
            ans[i] = temp[i];
        return ans;
    }

    private static int getMaxLevel(int length) {
        if (length % 2 != 0) length--;
        int level = (int)(Math.log(length)/Math.log(2));
        if (Math.pow(2, level) == length) return level;
        return (int)(Math.log(length/2)/Math.log(2));
    }

    public static List<List<DoublePoint>> getHaarWavelet(List<DoublePoint> dataset) {
        List<List<DoublePoint>> haar = new ArrayList<>();
        int length = dataset.get(0).getLength();
        int maxLevel = getMaxLevel(length);
        List<List<DoublePoint>> tmp = new ArrayList<>();
        for (int j=0; j<dataset.size(); j++) {
            double[] ans = dataset.get(j).getPoints();
            tmp.add(new ArrayList<>());
            for (int i=1; i<=maxLevel; i++) {
                ans = FWT(ans);
                tmp.get(j).add(new DoublePoint(ans));
            }
        }
        for (int i=maxLevel; i>0; i--) {
            haar.add(new ArrayList<>());
            for (int j=0; j<dataset.size(); j++) {
                haar.get(maxLevel - i).add(tmp.get(j).get(i - 1));
            }
        }
        return haar;
    }

}
