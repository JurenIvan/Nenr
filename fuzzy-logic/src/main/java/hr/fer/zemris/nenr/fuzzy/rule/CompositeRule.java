package hr.fer.zemris.nenr.fuzzy.rule;

import java.util.Map;
import java.util.function.BiFunction;

public class CompositeRule extends AbstractRule {

    public final SimpleRule[] rules;
    public BiFunction<Double, Double, Double> conclusionMachine = Math::min;

    public CompositeRule(SimpleRule... rules) {
        this.rules = rules;
    }

    @Override
    double getVal(Map<String, Integer> input) {
        double val = 1;
        for (var rule : rules) {
            val = conclusionMachine.apply(val, rule.getVal(input));
        }
        return val;
    }

    public void setConclusionMachine(BiFunction<Double, Double, Double> conclusionMachine) {
        this.conclusionMachine = conclusionMachine;
    }
}
