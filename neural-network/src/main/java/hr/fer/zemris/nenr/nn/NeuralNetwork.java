package hr.fer.zemris.nenr.nn;

import hr.fer.zemris.nenr.nn.outputter.Outputter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hr.fer.zemris.nenr.nn.NeuralNetwork.TrainMode.MINI_BATCH;
import static hr.fer.zemris.nenr.nn.NeuralNetwork.TrainMode.ONLINE;
import static java.lang.Math.pow;

public class NeuralNetwork {

    private final int[] layers;
    private final List<double[][]> w;
    private final List<double[]> w0;
    private final List<List<double[]>> y;
    private final List<List<double[]>> d;
    private final Outputter statusOutput;
    private final double learningRate;
    private final double eps;
    private final int iterationCap;


    public NeuralNetwork(double learningRate, double eps, int iterationCap, Outputter statusOutput, int... layers) {
        this.learningRate = learningRate;
        this.iterationCap = iterationCap;
        this.eps = eps;
        this.layers = layers;
        this.statusOutput = statusOutput == null ? (iteration, err) -> {
        } : statusOutput;

        int layersCount = layers.length - 1;
        this.w = new ArrayList<>(layersCount);
        this.w0 = new ArrayList<>(layersCount);
        this.y = new ArrayList<>(layersCount);
        this.d = new ArrayList<>(layersCount);

        initializeMemory(layers);
        initializeW();
        initializeW0();
    }

    public void fit(List<Sample> samples, TrainMode mode) {
        double err = error(samples);
        int iMax = 0;
        for (int i = 0; i < iterationCap && err > eps; i++) {
            if (i % 1000 == 0) statusOutput.output(i, err);

            if (mode == ONLINE) {
                train(List.of(samples.get((int) (Math.random() * samples.size()))));
            } else if (mode == MINI_BATCH) {
                train(selectSubset(samples, 2));
            } else {
                train(samples);
            }
            err = error(samples);
            i = iMax;
        }
        statusOutput.output(iMax, err);
    }

    private List<Sample> selectSubset(List<Sample> samples, int count) {
        List<Sample> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(samples.get((int) (Math.random() * samples.size())));
        }

        return result;
    }

    public double[] predict(Sample sample) {
        evaluate(sample, 0);
        var result = y.get(layers.length - 2).get(0);
        return Arrays.copyOf(result, result.length);
    }

    private void train(List<Sample> samples) {
        evaluate(samples);
        calculateDs(samples);
        backPropagation(samples);
    }

    private void backPropagation(List<Sample> samples) {
        for (int s = 0; s < samples.size(); s++) {
            double[] prev = samples.get(s).getSample();

            for (int layerIndex = 0; layerIndex < layers.length - 1; layerIndex++) {

                for (int i = 0; i < layers[layerIndex]; i++) {
                    for (int j = 0; j < layers[layerIndex + 1]; j++) {
                        w.get(layerIndex)[i][j] += learningRate * d.get(layerIndex).get(s)[j] * prev[i];
                        w0.get(layerIndex)[j] += learningRate * d.get(layerIndex).get(s)[j];
                    }
                }
                prev = y.get(layerIndex).get(s);
            }
        }
    }

    private void calculateDs(List<Sample> samples) {
        for (int i = 0; i < samples.size(); i++) {
            calculateDs(samples.get(i), i);
        }
    }

    public void calculateDs(Sample sample, int sampleIndex) {
        for (int layerIndex = layers.length - 2; layerIndex >= 0; layerIndex--) {
            if (d.get(layerIndex).size() <= sampleIndex) {
                d.get(layerIndex).add(sampleIndex, new double[layers[layerIndex + 1]]);
            }
        }
        double[] nextExpected = d.get(layers.length - 2).get(sampleIndex);
        for (int i = 0; i < layers[layers.length - 1]; i++) {
            var yActual = y.get(layers.length - 2).get(sampleIndex)[i];
            nextExpected[i] = yActual * (1 - yActual) * (sample.getExpected()[i] - yActual);
        }

        for (int layerIndex = layers.length - 2; layerIndex > 0; layerIndex--) {

            var curr = d.get(layerIndex - 1).get(sampleIndex);
            for (int i = 0; i < layers[layerIndex]; i++) {
                var yMine = y.get(layerIndex - 1).get(sampleIndex)[i];
                double sum = 0;
                for (int j = 0; j < layers[layerIndex + 1]; j++) {
                    sum += w.get(layerIndex)[i][j] * nextExpected[j];
                }
                curr[i] = yMine * (1 - yMine) * sum;
            }
            nextExpected = curr;
        }
    }


    private void evaluate(List<Sample> samples) {
        for (int i = 0; i < samples.size(); i++) {
            evaluate(samples.get(i), i);
        }
    }

    public void evaluate(Sample sample, int sampleIndex) {
        double[] yPrev = sample.getSample();
        for (int layerIndex = 0; layerIndex < layers.length - 1; layerIndex++) {

            if (y.get(layerIndex).size() <= sampleIndex) {
                y.get(layerIndex).add(sampleIndex, new double[layers[layerIndex + 1]]);
            }
            for (int i = 0; i < layers[layerIndex + 1]; i++) {
                double sum = 0;
                for (int j = 0; j < layers[layerIndex]; j++) {
                    sum += w.get(layerIndex)[j][i] * yPrev[j];
                }
                sum += w0.get(layerIndex)[i];
                double sigmSum = sigm(sum);
                y.get(layerIndex).get(sampleIndex)[i] = sigmSum;
            }

            yPrev = y.get(layerIndex).get(sampleIndex);
        }
    }

    public double error(List<Sample> samples) {
        double errSum = 0;
        for (var sample : samples) {
            var prediction = predict(sample);
            for (int i = 0; i < layers[layers.length - 1]; i++) {
                errSum += pow(prediction[i] - sample.getExpected()[i], 2);
            }
        }
        return 0.5 * 1 / samples.size() * errSum;
    }

    private void initializeMemory(int[] layers) {
        for (int i = 1; i < layers.length; i++) {
            w.add(new double[layers[i - 1]][layers[i]]);
            w0.add(new double[layers[i]]);
            y.add(new ArrayList<>(layers[i]));
            d.add(new ArrayList<>(layers[i]));
        }
    }

    private void initializeW() {
        for (int i = 0; i < w.size(); i++) {
            var matrix = w.get(i);
            for (int j = 0; j < matrix.length; j++) {
                var row = matrix[j];
                for (int k = 0; k < row.length; k++) {
                    matrix[j][k] = Math.random() * 0.8 - 0.4;
                }
            }
        }
    }

    private void initializeW0() {
        for (int j = 0; j < w0.size(); j++) {
            var row = w0.get(j);
            for (int k = 0; k < row.length; k++) {
                row[k] = Math.random() * 0.6 - 0.3;
            }
        }
    }

    public double sigm(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public enum TrainMode {
        ONLINE, MINI_BATCH, BATCH
    }
}
