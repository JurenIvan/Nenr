package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.rule.IRule;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

import java.util.List;
import java.util.Map;

import static hr.fer.zemris.nenr.fuzzy.operations.Operations.binaryOperation;
import static hr.fer.zemris.nenr.fuzzy.operations.Operations.zadehOr;

public abstract class AbstractFuzzySystem implements FuzzySystem {

    private final Defuzzifier defuzzifier;
    private final List<IRule> rules;

    public AbstractFuzzySystem(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
        rules = addRules();
    }

    @Override
    public double conclude(Map<String, Integer> input) {
        IFuzzySet ifs = rules.get(0).conclude(input);
        for (var rule : rules)
            ifs = binaryOperation(ifs, rule.conclude(input), zadehOr());
        return defuzzifier.defuzzify(ifs);
    }

    public List<IRule> getRules() {
        return rules;
    }
}
