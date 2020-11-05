package hr.fer.zemris.nenr.fuzzy.defuzzifier;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

public class COADefuzzyfier implements Defuzzifier {
    @Override
    public double defuzzify(IFuzzySet fuzzySet) {
        double nominator = 0, denominator = 0;

        for (var de : fuzzySet.getDomain()) {
            var valueAt = fuzzySet.getValueAt(de);
            nominator += valueAt * de.getComponentValue(0);
            denominator += valueAt;
        }
        return nominator / denominator;
    }
}
