package hr.fer.zemris.nenr;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

public class Runner {

    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);
    private static final List<double[]> SAMPLES = getSamples(-4, 4, -4, 4);

    public static void main(String[] args) {
        NeuroFuzzyNetwork anfis = new NeuroFuzzyNetwork(30, 81, 0.001, 0.001);
        anfis.train(SAMPLES, 100000);

        System.out.println(anfis.predict(new double[]{-4, -4}));
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
