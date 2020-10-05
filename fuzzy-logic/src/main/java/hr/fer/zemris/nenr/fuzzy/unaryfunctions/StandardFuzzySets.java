package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

import hr.fer.zemris.nenr.fuzzy.operations.TriFunction;

import java.util.function.BiFunction;

public class StandardFuzzySets {

    private static final BiFunction<Integer, Integer, IIntUnaryFunction> L_FUNCTION_GENERATOR =
            (a, b) -> (x) -> {
                if (x <= a) return 1;
                if (x >= b) return 0;

                return (b - x) / (double) (b - a);
            };

    private static final BiFunction<Integer, Integer, IIntUnaryFunction> GAMMA_FUNCTION_GENERATOR =
            (a, b) -> (x) -> {
                if (x <= a) return 0;
                if (x >= b) return 1;

                return (x - a) / (double) (b - a);
            };

    private static final TriFunction<Integer, Integer, Integer, IIntUnaryFunction> LABDA_FUNCTION_GENERATOR =
            (a, b, y) -> (x) -> {
                if (x <= a)
                    return 0;

                if (x < b)
                    return (x - a) / (double) (b - a);

                if (x < y)
                    return (y - x) / (double) (y - b);

                return 0;
            };

    private StandardFuzzySets() {
    }

    public static IIntUnaryFunction LFunction(int a, int b) {
        return L_FUNCTION_GENERATOR.apply(a, b);
    }

    public static IIntUnaryFunction gammaFunction(int a, int b) {
        return GAMMA_FUNCTION_GENERATOR.apply(a, b);
    }

    public static IIntUnaryFunction lambdaFunction(int a, int b, int y) {
        return LABDA_FUNCTION_GENERATOR.apply(a, b, y);
    }
}
