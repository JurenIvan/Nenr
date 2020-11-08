package hr.fer.zemris.nenr.ga.evaluator;

import hr.fer.zemris.nenr.ga.EntryProvider;
import hr.fer.zemris.nenr.ga.domain.Instance;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class FunctionEvaluator implements Evaluator<Instance> {

    private final List<double[]> values;
    private final BiFunction<Double, Double, Double> errorCollectingFuction;
    private final IFunction function;

    public FunctionEvaluator(EntryProvider entryProvider, IFunction function) {
        this(entryProvider, (expected, actual) -> Math.pow(expected - actual, 2), function);
    }

    public FunctionEvaluator(EntryProvider entryProvider, BiFunction<Double, Double, Double> errorCollectingFuction, IFunction function) {
        this.function = function;
        this.errorCollectingFuction = errorCollectingFuction;
        this.values = entryProvider.provideSamples();
    }

    public double evaluate(Instance instance) {
        double err = 0;
        for (var tuple : values) {
            err += errorCollectingFuction.apply(tuple[2], function.valueAt(Arrays.copyOf(tuple, 2), instance.getChromosomes()));
        }
        return err;
    }
}
