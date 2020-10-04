package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

public class GammaFunction implements IIntUnaryFunction {

    private final int a;
    private final int b;

    public GammaFunction(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double valueAt(int x) {
        if (x <= a) return 0;
        if (x >= b) return 1;

        return (x - a) / (double) (b - a);
    }
}
