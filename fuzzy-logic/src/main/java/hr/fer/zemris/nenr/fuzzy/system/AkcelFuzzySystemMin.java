package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;

import java.util.Map;

public class AkcelFuzzySystemMin implements FuzzySystem {

    private final Defuzzifier defuzzifier;

    public AkcelFuzzySystemMin(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    @Override
    public double conclude(Map<String, Integer> input) {
        return 10000;
    }
}
