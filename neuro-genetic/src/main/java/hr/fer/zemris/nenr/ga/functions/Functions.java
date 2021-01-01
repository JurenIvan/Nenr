package hr.fer.zemris.nenr.ga.functions;

import hr.fer.zemris.nenr.ga.evaluator.IFunction;

import java.util.function.Function;

import static java.lang.Math.*;
import static java.util.stream.IntStream.range;

public class Functions {

    public static final IFunction F1 = x -> 100 * pow(x[1] - pow(x[0], 2), 2) + pow(1 - x[0], 2);
    public static final Function<Integer, IFunction> F3_GENERATOR = order -> x -> range(0, order).mapToDouble(i -> Math.pow(x[i] - i, 2)).sum();

    public static final Function<Integer, IFunction> F6_GENERATOR = order -> x -> {
        double sumOfSquares = range(0, order).mapToDouble(i -> pow(x[i], 2)).sum();
        return 0.5 + (pow(sin(sqrt(sumOfSquares)), 2) - 0.5) / pow(1 + 0.001 * sumOfSquares, 2);
    };

    public static final Function<Integer, IFunction> F7_GENERATOR = order -> x -> {
        double sumOfSquares = range(0, order).mapToDouble(i -> pow(x[i], 2)).sum();
        return Math.pow(sumOfSquares, 0.25) * (1 + Math.pow(Math.sin(50 * Math.pow(sumOfSquares, 0.1)), 2));
    };
}
