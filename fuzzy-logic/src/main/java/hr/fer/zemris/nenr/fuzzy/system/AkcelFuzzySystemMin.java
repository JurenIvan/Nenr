package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.rule.IRule;

import java.util.List;

public class AkcelFuzzySystemMin extends AbstractFuzzySystem {

    public AkcelFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IRule> addRules() {
        return List.of();
    }
}
