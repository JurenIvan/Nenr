package hr.fer.zemris.nenr.fuzzy.operations;

@FunctionalInterface
public interface IBinaryFunction {

    double valueAt(double x1, double x2);
}
