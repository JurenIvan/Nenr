package hr.fer.zemris.nenr;

import java.util.List;

import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Collections.shuffle;

public class NeuroFuzzyNetwork {

    private final int ruleCount;
    private final int batchSize;
    private final double etaZ;
    private final double etaA;

    private final double[] a;
    private final double[] b;
    private final double[] c;
    private final double[] d;
    private final double[] p;
    private final double[] q;
    private final double[] r;
    private final double[] z;
    private final double[] alpha;
    private final double[] beta;
    private final double[] w;
    private final double[] dA;
    private final double[] dB;
    private final double[] dC;
    private final double[] dD;
    private final double[] dP;
    private final double[] dR;
    private final double[] dQ;

    public NeuroFuzzyNetwork(int ruleCount, int batchSize, double etaZ, double etaA) {
        this.ruleCount = ruleCount;
        this.batchSize = batchSize;
        this.etaZ = etaZ;
        this.etaA = etaA;

        alpha = new double[ruleCount];
        beta = new double[ruleCount];
        w = new double[ruleCount];
        dA = new double[ruleCount];
        dB = new double[ruleCount];
        dC = new double[ruleCount];
        dD = new double[ruleCount];
        dP = new double[ruleCount];
        dR = new double[ruleCount];
        dQ = new double[ruleCount];
        a = initialize();
        b = initialize();
        c = initialize();
        d = initialize();
        p = initialize();
        q = initialize();
        r = initialize();
        z = initialize();
    }

    public void train(List<double[]> samples, int iterationCount) {
        for (int iteration = 0; iteration <= iterationCount; iteration++) {

            initializeBatchElems();
            if (batchSize < samples.size())
                shuffle(samples);

            for (int sampleOrder = 0; sampleOrder < batchSize; sampleOrder++) {
                double x = samples.get(sampleOrder)[0];
                double y = samples.get(sampleOrder)[1];
                double expectedOut = samples.get(sampleOrder)[2];

                double wSum = 0;
                double output = 0.0;
                for (int i = 0; i < ruleCount; i++) {
                    alpha[i] = 1.0 / (1.0 + exp(b[i] * (x - a[i])));
                    beta[i] = 1.0 / (1.0 + exp(d[i] * (y - c[i])));
                    w[i] = alpha[i] * beta[i];
                    wSum += w[i];
                    z[i] = p[i] * x + q[i] * y + r[i];
                    output += z[i] * w[i];
                }

                output /= wSum;

                double err = expectedOut - output;

                for (int i = 0; i < ruleCount; i++) {
                    double common = err * (w[i] / wSum);
                    dP[i] += common * x;
                    dQ[i] += common * y;
                    dR[i] += common;

                    double weirdo = 0;
                    for (int j = 0; j < ruleCount; j++) {
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

            for (int i = 0; i < ruleCount; i++) {
                update(i);
            }

            if (iteration % 5000 == 0) {
                double err = 0;

                for (double[] sample : samples) {
                    double x = sample[0];
                    double y = sample[1];
                    double expectedOut = sample[2];

                    double wSum = 0;
                    double output = 0.0;

                    for (int i = 0; i < ruleCount; i++) {
                        double alpha = 1.0 / (1.0 + exp(b[i] * (x - a[i])));
                        double beta = 1.0 / (1.0 + exp(d[i] * (y - c[i])));
                        double w = alpha * beta;
                        wSum += w;
                        output += (p[i] * x + q[i] * y + r[i]) * w;
                    }
                    output /= wSum;
                    err += pow(expectedOut - output, 2);
                }
                System.out.printf("iteration:%8d %s%n", iteration, 0.5 * err / samples.size());
            }
        }
    }

    public double predict(double[] numbers) {
        double x = numbers[0];
        double y = numbers[1];

        double wSum = 0;
        double output = 0.0;

        for (int i = 0; i < ruleCount; i++) {
            double alpha = 1.0 / (1.0 + exp(b[i] * (x - a[i])));
            double beta = 1.0 / (1.0 + exp(d[i] * (y - c[i])));
            double w = alpha * beta;
            wSum += w;
            output += (p[i] * x + q[i] * y + r[i]) * w;
        }
        output /= wSum;
        return output;
    }


    public void update(int i) {
        a[i] += etaA * dA[i];
        b[i] += etaA * dB[i];
        c[i] += etaA * dC[i];
        d[i] += etaA * dD[i];

        p[i] += etaZ * dP[i];
        q[i] += etaZ * dQ[i];
        r[i] += etaZ * dR[i];
    }

    private void initializeBatchElems() {
        for (var d : List.of(dA, dB, dC, dD, dP, dR, dQ)) fill(d, 0);
    }

    private double[] initialize() {
        double[] a = new double[ruleCount];
        for (int i = 0; i < a.length; i++) {
            double RAND_SCALE = 1;
            a[i] = 2 * random() * RAND_SCALE - RAND_SCALE;
        }
        return a;
    }

}
