package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;

import java.util.Map;

public class KormiloFuzzySystemMin implements FuzzySystem {

    private final Defuzzifier defuzzifier;

    public KormiloFuzzySystemMin(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    @Override
    public double conclude(Map<String, Integer> input) {
        return 0;
    }
}
