package hr.fer.zemris.nenr.fuzzy.operations;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

public class Operations {

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
        return x -> 1 - x;
    }

    public static IBinaryFunction zadehAnd() {
        return Math::min;
    }

    public static IBinaryFunction zadehOr() {
        return Math::max;
    }

    public static IBinaryFunction hamacherTNorm(double p) {
        return (a, b) -> (a * b) / (p + (1 - p) * (a + b - a * b));
    }

    public static IBinaryFunction hamacherSNorm(double p) {
        return (a, b) -> (a + b - (2 - p) * a * b) / (1 - (1 - p) * a * b);
    }

}
