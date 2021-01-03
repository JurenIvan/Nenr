package hr.fer.zemris.nenr.geneticalgorithm.evaluator;

import hr.fer.zemris.nenr.geneticalgorithm.CountingFunction;

public interface Evaluator<T> {

    double evaluateErrorOnDataset(T instance);

    double[] predict(T instance, double[] sample);

    double[] evaluateDoubleValue(T instance);

    CountingFunction<double[], Double> getFunction();
}
