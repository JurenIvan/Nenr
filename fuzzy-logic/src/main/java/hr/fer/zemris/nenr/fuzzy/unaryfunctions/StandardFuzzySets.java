package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

public class StandardFuzzySets {

    private StandardFuzzySets() {
    }

    public static IIntUnaryFunction lFunction(int a, int b) {
        return (x) -> {
            if (x <= a) return 1;
            if (x >= b) return 0;

            return (b - x) / (double) (b - a);
        };
    }

    public static IIntUnaryFunction gammaFunction(int a, int b) {
        return (x) -> {
            if (x <= a) return 0;
            if (x >= b) return 1;

            return (x - a) / (double) (b - a);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int a, int b, int y) {
        return (x) -> {
            if (x <= a)
                return 0;

            if (x < b)
                return (x - a) / (double) (b - a);

            if (x < y)
                return (y - x) / (double) (y - b);

            return 0;
        };
    }
}
