package hr.fer.zemris.nenr.fuzzy.rule;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

import java.util.Map;

public class SimpleRule extends AbstractRule {

    private final String key;
    private final IFuzzySet fuzzySet;

    public SimpleRule(String key, IFuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        this.key = key;
    }

    public SimpleRule(String key, IFuzzySet fuzzySet, IFuzzySet consequence) {
        super(consequence);
        this.fuzzySet = fuzzySet;
        this.key = key;
    }


    @Override
    double getVal(Map<String, Integer> input) {
        Integer inValue = input.get(key);
        if (inValue == null) throw new IllegalArgumentException("No value to apply to rule");

        return fuzzySet.getValueAt(DomainElement.of(inValue));
    }
}
