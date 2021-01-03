package hr.fer.zemris.nenr.geneticalgorithm.evaluator;

import hr.fer.zemris.nenr.geneticalgorithm.CountingFunction;
import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;

import java.util.function.BiFunction;

public class FunctionEvaluatorDouble implements Evaluator<InstanceDouble> {

    private final BiFunction<Double, Double, Double> errorCollectingFuction;
    private final CountingFunction<double[], Double> function;

    public FunctionEvaluatorDouble(IFunction function) {
        this((expected, actual) -> Math.abs(expected - actual), function);
    }

    public FunctionEvaluatorDouble(BiFunction<Double, Double, Double> errorCollectingFuction, IFunction function) {
        this.function = new CountingFunction<>(function::valueAt);
        this.errorCollectingFuction = errorCollectingFuction;
    }

    public double evaluateErrorOnDataset(InstanceDouble instance) {
        return errorCollectingFuction.apply(0.0, function.apply(instance.getChromosomes()));
    }

    @Override
    public double[] predict(InstanceDouble instance, double[] sample) {
        var result = new double[1];
        result[0] = function.apply(instance.getChromosomes());
        return result;
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
