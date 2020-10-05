package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MutableFuzzySetTest {

    @Test
    public void MutableFuzzySetConstructorCalled_argumentIsNull_exceptionIsThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> new MutableFuzzySet(null));
    }

    @Test
    public void getDomain_domainIsReturned() {
        var domain = Domain.intRange(0, 5);
        var fuzzy = new MutableFuzzySet(domain);

        Assertions.assertEquals(domain, fuzzy.getDomain());
    }

    @Test
    public void getValueAt_and_set_works() {
        var domain = Domain.intRange(0, 5);
        var fuzzy = new MutableFuzzySet(domain);

        fuzzy.set(DomainElement.of(1), 123);

        Assertions.assertEquals(123, fuzzy.getValueAt(DomainElement.of(1)));
    }
}