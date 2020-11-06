package hr.fer.zemris.nenr.fuzzy.defuzzifier;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class COADefuzzyfierTest {

    @Test
    void defuzzify() {

        MutableFuzzySet fuzzySet = new MutableFuzzySet(new SimpleDomain(6, 15));

        fuzzySet.set(DomainElement.of(6), 0);
        fuzzySet.set(DomainElement.of(7), 1 / 3.0);
        fuzzySet.set(DomainElement.of(8), 2 / 3.0);
        fuzzySet.set(DomainElement.of(9), 1);
        fuzzySet.set(DomainElement.of(10), 2 / 3.0);
        fuzzySet.set(DomainElement.of(11), 1 / 2.0);
        fuzzySet.set(DomainElement.of(12), 1);
        fuzzySet.set(DomainElement.of(13), 1 / 2.0);
        fuzzySet.set(DomainElement.of(14), 0);

        Defuzzifier defuzzifier = new COADefuzzyfier();
        Assertions.assertEquals(71 / 7.0, defuzzifier.defuzzify(fuzzySet), 1e-5);
    }
}
