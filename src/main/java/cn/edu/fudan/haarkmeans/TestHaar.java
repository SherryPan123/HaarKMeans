package cn.edu.fudan.haarkmeans;

/**
 * Created by sherry on 18-2-4.
 */
public class TestHaar {

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

    public static void main(String[] args) {
        double[][] data = {{1,5,4,8,2,1,4,3}, {5,4,8,2,1,4,3,7}, {4,8,2,1,4,3,7,1}, {8,2,1,4,3,7,1,5}};
        int maxLevel = getMaxLevel(data[0].length);
        for (int j=0; j<data.length; j++) {
            double[] ans = data[j];
            for (int i=1; i<=maxLevel; i++) {
                ans = FWT(ans);
            }
            System.out.println(ans);
        }
    }

}
