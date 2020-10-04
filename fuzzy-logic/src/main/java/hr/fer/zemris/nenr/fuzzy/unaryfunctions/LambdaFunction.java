package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

public class LambdaFunction implements IIntUnaryFunction {

    private final int a;
    private final int b;
    private final int y;

    public LambdaFunction(int a, int b, int y) {
        this.a = a;
        this.b = b;
        this.y = y;
    }

    @Override
    public double valueAt(int x) {
        if (x <= a)
            return 0;

        if (x < b)
            return (x - a) / (double) (b - a);

        if (x < y)
            return (y - x) / (double) (y - b);

        return 0;
    }
}
