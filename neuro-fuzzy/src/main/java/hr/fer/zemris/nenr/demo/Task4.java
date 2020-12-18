package hr.fer.zemris.nenr.demo;

import hr.fer.zemris.nenr.NeuroFuzzyNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

public class Task4 {


    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);
    private static final List<double[]> SAMPLES = getSamples(-4, 4, -4, 4);

    public static void main(String[] args) {
        //part A
        NeuroFuzzyNetwork anfis1 = new NeuroFuzzyNetwork(1, 81, 0.001, 0.001);
        NeuroFuzzyNetwork anfis2 = new NeuroFuzzyNetwork(2, 81, 0.001, 0.001);
        NeuroFuzzyNetwork anfis6 = new NeuroFuzzyNetwork(6, 81, 0.001, 0.001);

        anfis1.train(SAMPLES, 100000);
        anfis2.train(SAMPLES, 100000);
        anfis6.train(SAMPLES, 100000);

        Outputter.write3DToFile("task-4a-1-rule.txt", getOutput(anfis1, SAMPLES));
        Outputter.write3DToFile("task-4a-2-rule.txt", getOutput(anfis2, SAMPLES));
        Outputter.write3DToFile("task-4a-6-rule.txt", getOutput(anfis6, SAMPLES));

        //part B
        Outputter.write3DToFile("task-4b-1-rule.txt", getError(anfis1, SAMPLES));
        Outputter.write3DToFile("task-4b-2-rule.txt", getError(anfis2, SAMPLES));
        Outputter.write3DToFile("task-4b-6-rule.txt", getError(anfis6, SAMPLES));
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

    public static List<double[]> getOutput(NeuroFuzzyNetwork anfis, List<double[]> samples) {
        List<double[]> result = new ArrayList<>(81);
        for (var sample : samples) {
            var prediction = anfis.predict(Arrays.copyOf(sample, 2));
            var arr = new double[3];
            arr[0] = sample[0];
            arr[1] = sample[1];
            arr[2] = prediction;
            result.add(arr);
        }
        return result;
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
}
