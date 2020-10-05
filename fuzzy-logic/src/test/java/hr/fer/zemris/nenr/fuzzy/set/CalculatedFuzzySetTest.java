package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.unaryfunctions.StandardFuzzySets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatedFuzzySetTest {

    @Test
    public void MutableFuzzySetConstructorCalled_argumentIsNull_exceptionIsThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> new CalculatedFuzzySet(null, null));
        Assertions.assertThrows(NullPointerException.class, () -> new CalculatedFuzzySet(Domain.intRange(0, 1), null));
        Assertions.assertThrows(NullPointerException.class, () -> new CalculatedFuzzySet(null, StandardFuzzySets.gammaFunction(1, 3)));
    }

    @Test
    public void getDomain_domainIsReturned() {
        var domain = Domain.intRange(0, 5);
        var fuzzy = new CalculatedFuzzySet(domain, StandardFuzzySets.gammaFunction(1, 3));

        Assertions.assertEquals(domain, fuzzy.getDomain());
    }

    @Test
    public void getValueAt_and_set_works() {
        var domain = Domain.intRange(0, 11);
        var fuzzy = new CalculatedFuzzySet(domain, StandardFuzzySets.gammaFunction(0, 10));

        Assertions.assertEquals(0.1, fuzzy.getValueAt(DomainElement.of(1)));
        Assertions.assertEquals(0.2, fuzzy.getValueAt(DomainElement.of(2)));
    }

}