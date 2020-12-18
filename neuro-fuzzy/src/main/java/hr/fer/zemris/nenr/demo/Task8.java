package hr.fer.zemris.nenr.demo;

import hr.fer.zemris.nenr.NeuroFuzzyNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

public class Task8 {


    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);
    private static final List<double[]> SAMPLES = getSamples(-4, 4, -4, 4);

    public static void main(String[] args) {
        NeuroFuzzyNetwork anfis_B_5 = new NeuroFuzzyNetwork(12, 81, 0.5, 0.5);
        NeuroFuzzyNetwork anfis_B_001 = new NeuroFuzzyNetwork(12, 81, 0.001, 0.001);
        NeuroFuzzyNetwork anfis_B_00001 = new NeuroFuzzyNetwork(12, 81, 0.00001, 0.00001);

        NeuroFuzzyNetwork anfis_O_5 = new NeuroFuzzyNetwork(12, 1, 0.5, 0.5);
        NeuroFuzzyNetwork anfis_O_001 = new NeuroFuzzyNetwork(12, 1, 0.001, 0.001);
        NeuroFuzzyNetwork anfis_O_00001 = new NeuroFuzzyNetwork(12, 1, 0.00001, 0.00001);


        System.out.println("anfis_B_5");
        anfis_B_5.train(SAMPLES, 100, 1);
        System.out.println("anfis_B_001");
        anfis_B_001.train(SAMPLES, 100, 1);
        System.out.println("anfis_B_00001");
        anfis_B_00001.train(SAMPLES, 100, 1);
        System.out.println("anfis_O_5");
        anfis_O_5.train(SAMPLES, 8100, 81);
        System.out.println("anfis_O_001");
        anfis_O_001.train(SAMPLES, 8100, 81);
        System.out.println("anfis_O_00001");
        anfis_O_00001.train(SAMPLES, 8100, 81);
    }

    public static List<double[]> getError(NeuroFuzzyNetwork anfis, List<double[]> samples) {
        List<double[]> result = new ArrayList<>(81);
        for (var sample : samples) {
            var prediction = anfis.predict(Arrays.copyOf(sample, 2));
            var arr = new double[3];
            arr[0] = sample[0];
            arr[1] = sample[1];
            arr[2] = sample[2] - prediction;
            result.add(arr);
        }
        return result;
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
