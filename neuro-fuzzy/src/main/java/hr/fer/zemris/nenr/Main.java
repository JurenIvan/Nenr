package hr.fer.zemris.nenr;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Collections.shuffle;

public class Main {

    private static final BiFunction<Integer, Integer, Double> FUNCTION = (x, y) -> pow(cos(x / 5.0), 2) * (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3);

    private static final int RULE_COUNT = 10;
    private static final int BATCH_SIZE = 81;
    private static final double ETA_Z = 0.001;
    private static final double ETA_A = 0.001;
    private static final double RAND_SCALE = 1;
    private static final int ITERATION_COUNT = 1_000_000;

    private static final double[] a = initialize();
    private static final double[] b = initialize();
    private static final double[] c = initialize();
    private static final double[] d = initialize();
    private static final double[] p = initialize();
    private static final double[] q = initialize();
    private static final double[] r = initialize();
    private static final double[] z = initialize();
    private static final List<double[]> samples = getSamples(-4, 4, -4, 4);

    private static final double[] alpha = new double[RULE_COUNT];
    private static final double[] beta = new double[RULE_COUNT];
    private static final double[] w = new double[RULE_COUNT];

    private static final double[] dA = new double[RULE_COUNT];
    private static final double[] dB = new double[RULE_COUNT];
    private static final double[] dC = new double[RULE_COUNT];
    private static final double[] dD = new double[RULE_COUNT];
    private static final double[] dP = new double[RULE_COUNT];
    private static final double[] dR = new double[RULE_COUNT];
    private static final double[] dQ = new double[RULE_COUNT];

    public static void main(String[] args) {
        for (int iteration = 0; iteration < ITERATION_COUNT; iteration++) {

            initializeBatchElems();
            if (BATCH_SIZE < samples.size()) {
                shuffle(samples);
            }
            for (int sampleOrder = 0; sampleOrder < BATCH_SIZE; sampleOrder++) {
                double x = samples.get(sampleOrder)[0];
                double y = samples.get(sampleOrder)[1];
                double expectedOut = samples.get(sampleOrder)[2];

                double wSum = 0;
                double output = 0.0;
                for (int i = 0; i < RULE_COUNT; i++) {
                    alpha[i] = 1.0 / (1.0 + exp(b[i] * (x - a[i])));
                    beta[i] = 1.0 / (1.0 + exp(d[i] * (y - c[i])));
                    w[i] = alpha[i] * beta[i];
                    wSum += w[i];
                    z[i] = p[i] * x + q[i] * y + r[i];
                    output += z[i] * w[i];
                }

                output /= wSum;

                double err = expectedOut - output;

                for (int i = 0; i < RULE_COUNT; i++) {
                    double common = err * (w[i] / wSum);
                    dP[i] += common * x;
                    dQ[i] += common * y;
                    dR[i] += common;

                    double weirdo = 0;
                    for (int j = 0; j < RULE_COUNT; j++) {
                        if (i == j) continue;
                        weirdo += w[j] * (z[i] - z[j]);
                    }

                    common = err * (weirdo / (wSum * wSum));
                    double a1a = (1 - alpha[i]) * alpha[i];
                    double b1b = (1 - beta[i]) * beta[i];
                    dA[i] += common * beta[i] * b[i] * a1a;
                    dB[i] += common * beta[i] * (a[i] - x) * a1a;
                    dC[i] += common * alpha[i] * d[i] * b1b;
                    dD[i] += common * alpha[i] * (c[i] - y) * b1b;
                }
            }

            for (int i = 0; i < RULE_COUNT; i++) {
                update(i);
            }

            if (iteration % 100000 == 0) {
                double err = 0;

                for (double[] sample : samples) {
                    double x = sample[0];
                    double y = sample[1];
                    double expectedOut = sample[2];

                    double wSum = 0;
                    double output = 0.0;

                    for (int i = 0; i < RULE_COUNT; i++) {
                        double alpha = 1.0 / (1.0 + exp(b[i] * (x - a[i])));
                        double beta = 1.0 / (1.0 + exp(d[i] * (y - c[i])));
                        double w = alpha * beta;
                        wSum += w;
                        output += (p[i] * x + q[i] * y + r[i]) * w;
                    }
                    output /= wSum;
                    err += pow(expectedOut - output, 2);
                }
                System.out.println(LocalDateTime.now());
                System.out.printf("iteration:%8d %s%n", iteration, 0.5 * err / samples.size());
            }
        }
    }

    public static void update(int i) {
        a[i] += ETA_A * dA[i];
        b[i] += ETA_A * dB[i];
        c[i] += ETA_A * dC[i];
        d[i] += ETA_A * dD[i];

        p[i] += ETA_Z * dP[i];
        q[i] += ETA_Z * dQ[i];
        r[i] += ETA_Z * dR[i];
    }

    private static void initializeBatchElems() {
        for (var d : List.of(dA, dB, dC, dD, dP, dR, dQ)) fill(d, 0);
    }


    private static double[] initialize() {
        double[] a = new double[RULE_COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 2 * random() * RAND_SCALE - RAND_SCALE;
        }
        return a;
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
