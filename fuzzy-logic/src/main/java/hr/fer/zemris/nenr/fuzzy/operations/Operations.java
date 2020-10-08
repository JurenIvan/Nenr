package hr.fer.zemris.nenr.fuzzy.operations;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.Objects;

public class Operations {

    private static final IUnaryFunction ZADEH_NOT = x -> 1 - x;
    private static final IBinaryFunction ZADEH_AND = Math::min;
    private static final IBinaryFunction ZADEH_OR = Math::max;

    private Operations() {
    }

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        Objects.requireNonNull(set);
        Objects.requireNonNull(function);

        MutableFuzzySet newSet = new MutableFuzzySet(set.getDomain());
        for (var elem : newSet.getDomain())
            newSet.set(elem, function.valueAt(set.getValueAt(elem)));
        return newSet;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
        Objects.requireNonNull(set1);
        Objects.requireNonNull(set2);
        Objects.requireNonNull(function);
        if(!set1.getDomain().equals(set2.getDomain()))
            throw new IllegalArgumentException("Sets must have equal domains");

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
        return (a, b) -> (a * b) / (p + (1 - p) * (a + b - a * b));
    }

    public static IBinaryFunction hamacherSNorm(double p) {
        return (a, b) -> (a + b - (2 - p) * a * b) / (1 - (1 - p) * a * b);
    }
}
