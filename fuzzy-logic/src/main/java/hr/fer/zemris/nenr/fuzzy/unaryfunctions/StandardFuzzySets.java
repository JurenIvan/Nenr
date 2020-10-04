package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

public class StandardFuzzySets {

    private StandardFuzzySets() {
    }

    public static IIntUnaryFunction LFunction(int from, int to) {
        return new LFunction(from,to);
    }

    public static IIntUnaryFunction gammaFunction(int from, int to) {
        return new GammaFunction(from,to);
    }

    public static IIntUnaryFunction lambdaFunction(int from, int middle, int to) {
        return new LambdaFunction(from,middle,to);
    }
}
