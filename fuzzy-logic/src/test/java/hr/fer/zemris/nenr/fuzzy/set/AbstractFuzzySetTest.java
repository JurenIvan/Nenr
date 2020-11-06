package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractFuzzySetTest {

    @Test
    void testToString() {
        MutableFuzzySet fuzzySet = new MutableFuzzySet(new SimpleDomain(0, 3));
        fuzzySet.set(DomainElement.of(0), 0);
        fuzzySet.set(DomainElement.of(1), 0.1);
        fuzzySet.set(DomainElement.of(2), 0.2);

        Assertions.assertEquals("FuzzySet{\n0 : 0.0\n1 : 0.1\n2 : 0.2}", fuzzySet.toString());
    }
}
