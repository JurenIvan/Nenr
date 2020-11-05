package hr.fer.zemris.nenr.fuzzy.rule;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class AbstractRule implements IRule {

    private IFuzzySet consequence;
    private BiFunction<Double, Double, Double> funk = (x, y) -> x * y;

    public AbstractRule(IFuzzySet consequence) {
        this.consequence = consequence;
    }

    public AbstractRule() {
    }

    @Override
    public IFuzzySet conclude(Map<String, Integer> input) {
        MutableFuzzySet conclusion = new MutableFuzzySet(consequence.getDomain());
        consequence.getDomain().forEach(e -> conclusion.set(e, funk.apply(consequence.getValueAt(e), getVal(input))));
        return conclusion;
    }

    abstract double getVal(Map<String, Integer> input);

    public void setConsequence(IFuzzySet consequence) {
        this.consequence = consequence;
    }

    public void setFunk(BiFunction<Double, Double, Double> funk) {
        this.funk = funk;
    }
}
