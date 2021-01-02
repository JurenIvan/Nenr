package hr.fer.zemris.nenr.geneticalgorithm.evaluator;

import hr.fer.zemris.nenr.geneticalgorithm.CountingFunction;

public interface Evaluator<T> {

    double evaluate(T instance);

    double[] evaluateDoubleValue(T instance);

    CountingFunction<double[], Double> getFunction();
}
