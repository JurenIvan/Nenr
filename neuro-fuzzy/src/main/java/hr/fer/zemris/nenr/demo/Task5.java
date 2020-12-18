package hr.fer.zemris.nenr.demo;

import hr.fer.zemris.nenr.NeuroFuzzyNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.*;

public class Task5 {


    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);
    private static final List<double[]> SAMPLES = getSamples(-4, 4, -4, 4);

    public static void main(String[] args) {
        NeuroFuzzyNetwork anfis6 = new NeuroFuzzyNetwork(6, 81, 0.001, 0.001);
        anfis6.train(SAMPLES, 100000);

        Outputter.write3DToFile("task-5.txt", getFunctions(anfis6));
    }

    private static List<double[]> getFunctions(NeuroFuzzyNetwork anfis6) {
        List<double[]> result = new ArrayList<>(81);
        for (double i = -4; i <= 4; i += 0.1) {
            double[] line = new double[13];
            line[0] = i;
            line[1] = sigm(anfis6.getA()[0], anfis6.getB()[0], i);
            line[2] = sigm(anfis6.getC()[0], anfis6.getD()[0], i);

            line[3] = sigm(anfis6.getA()[1], anfis6.getB()[1], i);
            line[4] = sigm(anfis6.getC()[1], anfis6.getD()[1], i);

            line[5] = sigm(anfis6.getA()[2], anfis6.getB()[2], i);
            line[6] = sigm(anfis6.getC()[2], anfis6.getD()[2], i);

            line[7] = sigm(anfis6.getA()[3], anfis6.getB()[3], i);
            line[8] = sigm(anfis6.getC()[3], anfis6.getD()[3], i);

            line[9] = sigm(anfis6.getA()[4], anfis6.getB()[4], i);
            line[10] = sigm(anfis6.getC()[4], anfis6.getD()[4], i);

            line[11] = sigm(anfis6.getA()[5], anfis6.getB()[5], i);
            line[12] = sigm(anfis6.getC()[5], anfis6.getD()[5], i);
            result.add(line);
        }
        return result;
    }

    public static double sigm(double a, double b, double x) {
        return 1.0 / (1.0 + exp(b * (x - a)));
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
