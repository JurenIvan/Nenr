package hr.fer.zemris.nenr.geneticalgorithm.evaluator;

import hr.fer.zemris.nenr.Dataset;
import hr.fer.zemris.nenr.geneticalgorithm.CountingFunction;
import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;

import java.util.function.BiFunction;

import static java.lang.Math.pow;
import static java.lang.System.arraycopy;

public class NeuralNetworkEvaluator implements Evaluator<InstanceDouble> {

    private final CountingFunction<double[], Double> function = new CountingFunction<>(e -> -1.0);

    private final BiFunction<Double, Double, Double> errorCollectingFunction;
    private final Dataset dataset;
    private final int[] layers;
    private int numberOfParameters;

    public NeuralNetworkEvaluator(Dataset dataset, int... hiddenLayers) {
        this(dataset, (expected, actual) -> pow(expected - actual, 2), hiddenLayers);
    }

    public NeuralNetworkEvaluator(Dataset dataset, BiFunction<Double, Double, Double> errorCollectingFunction, int... hiddenLayers) {
        if (hiddenLayers.length < 1) throw new IllegalStateException("Should be atleast 3 layers deep");
        this.errorCollectingFunction = errorCollectingFunction;
        this.dataset = dataset;
        this.layers = new int[hiddenLayers.length + 2];

        this.layers[0] = dataset.getTotalArguments() - dataset.getResult();
        arraycopy(hiddenLayers, 0, layers, 1, hiddenLayers.length);
        this.layers[this.layers.length - 1] = dataset.getResult();

        calculateNumberOfParameters(layers);
    }

    private void calculateNumberOfParameters(int[] layers) {
        numberOfParameters = 2 * layers[0] * layers[1];
        for (int i = 2; i < layers.length; i++) {
            numberOfParameters += (layers[i - 1] + 1) * layers[i];
        }
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    @Override
    public double evaluate(InstanceDouble instance) {
        double err = 0;
        for (var sample : dataset.getSamples()) {
            var result = calculateOutput(instance, sample);
            int inCount = dataset.getTotalArguments() - dataset.getResult();
            for (int i = inCount; i < dataset.getTotalArguments(); i++) {
                err += errorCollectingFunction.apply(result[i - inCount], sample[i]);
            }
        }
        return err / dataset.size();
    }

    private double[] calculateOutput(InstanceDouble instance, double[] sample) {
        function.apply(null);
        var params = instance.getChromosomes();

        double[][] output = new double[layers.length - 1][];
        for (int i = 1; i < layers.length; i++) {
            output[i - 1] = new double[layers[i]];
        }

        int index = 0;
        for (int i = 0; i < layers[1]; i++) {
            double sum = 0;
            for (int j = 0; j < layers[0]; j++) {
                sum += Math.abs(sample[j] - params[index++]) / Math.abs(params[index++]);
            }
            output[0][i] = 1.0 / (1.0 + sum);
        }

        for (int layerIndex = 1; layerIndex < layers.length - 1; layerIndex++) {
            for (int i = 0; i < layers[layerIndex + 1]; i++) {
                double sum = 0;
                for (int j = 0; j < layers[layerIndex]; j++) {
                    sum += output[layerIndex - 1][j] * params[index++];
                }
                sum += params[index++];
                output[layerIndex][i] = 1.0 / (1.0 + Math.exp(-sum));
            }
        }

        return output[output.length - 1];
    }

    @Override
    public double[] evaluateDoubleValue(InstanceDouble instance) {
        return instance.getChromosomes();
    }

    @Override
    public CountingFunction<double[], Double> getFunction() {
        return function;
    }
}
