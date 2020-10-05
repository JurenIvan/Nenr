package hr.fer.zemris.nenr.fuzzy.operations;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.function.Function;

public class Operations {

    private static final IUnaryFunction ZADEH_NOT = x -> 1 - x;
    private static final IBinaryFunction ZADEH_AND = Math::min;
    private static final IBinaryFunction ZADEH_OR = Math::max;
    private static final Function<Double, IBinaryFunction> HAMACHER_T_NORM_GENERATOR = (p) -> (a, b) -> (a * b) / (p + (1 - p) * (a + b - a * b));
    private static final Function<Double, IBinaryFunction> HAMACHER_S_NORM_GENERATOR = (p) -> (a, b) -> (a + b - (2 - p) * a * b) / (1 - (1 - p) * a * b);

    private Operations() {
    }

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet newSet = new MutableFuzzySet(set.getDomain());
        for (var elem : newSet.getDomain())
            newSet.set(elem, function.valueAt(set.getValueAt(elem)));
        return newSet;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
        MutableFuzzySet newSet = new MutableFuzzySet(set1.getDomain());
        for (var elem : newSet.getDomain())
            newSet.set(elem, function.valueAt(set1.getValueAt(elem), set2.getValueAt(elem)));
        return newSet;
    }

    public static IUnaryFunction zadehNot() {
        return ZADEH_NOT;
    }

    public static IBinaryFunction zadehAnd() {
        return ZADEH_AND;
    }

    public static IBinaryFunction zadehOr() {
        return ZADEH_OR;
    }

    public static IBinaryFunction hamacherTNorm(double p) {
        return HAMACHER_T_NORM_GENERATOR.apply(p);
    }

    public static IBinaryFunction hamacherSNorm(double p) {
        return HAMACHER_S_NORM_GENERATOR.apply(p);
    }
}
