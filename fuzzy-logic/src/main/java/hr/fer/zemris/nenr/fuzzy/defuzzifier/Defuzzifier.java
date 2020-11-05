package hr.fer.zemris.nenr.fuzzy.defuzzifier;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

public interface Defuzzifier {

    double defuzzify(IFuzzySet fuzzySet);
}
