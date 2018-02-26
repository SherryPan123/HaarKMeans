package cn.edu.fudan.haarkmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherry on 18-2-5.
 */
public class FileRead {

    public static List<DoublePoint> readFromFile(String filename) {
        List<DoublePoint> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] valStr = line.trim().split(" ");
                List<String> valTrim = new ArrayList<>();
                for (int i=0; i<valStr.length; i++) {
                    if (!valStr[i].trim().isEmpty()){
                        valTrim.add(valStr[i]);
                    }
                }
                double[] val = new double[valTrim.size()];
                for (int i=0; i<valTrim.size(); i++) {
                    val[i] = Double.valueOf(valTrim.get(i));
                }
                points.add(new DoublePoint(val));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

}
