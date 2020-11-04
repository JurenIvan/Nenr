package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.rule.IRule;

import java.util.List;

public class KormiloFuzzySystemMin extends AbstractFuzzySystem {

    public KormiloFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IRule> addRules() {
        return List.of();
    }
}
