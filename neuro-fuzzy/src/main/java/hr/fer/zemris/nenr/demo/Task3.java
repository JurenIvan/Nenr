package hr.fer.zemris.nenr.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

public class Task3 {

    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);

    public static void main(String[] args) {
        Outputter.write3DToFile("task3-data.txt", getSamples(-4, 4, -4, 4));
    }

    public static List<double[]> getSamples(int xMin, int xMax, int yMin, int yMax) {
        List<double[]> result = new ArrayList<>(81);
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                double[] line = new double[3];
                line[0] = i;
                line[1] = j;
                line[2] = FUNCTION.apply(i, j);
                result.add(line);
            }
        }
        return result;
    }
}
