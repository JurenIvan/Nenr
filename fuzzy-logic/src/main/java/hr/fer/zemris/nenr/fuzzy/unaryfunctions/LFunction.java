package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

public class LFunction implements IIntUnaryFunction {

    private final int a;
    private final int b;

    public LFunction(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double valueAt(int x) {
        if (x <= a) return 1;
        if (x >= b) return 0;

        return (b - x) / (double) (b - a);
    }
}
