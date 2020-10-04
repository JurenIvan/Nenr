package hr.fer.zemris.nenr.domain;

import hr.fer.zemris.nenr.domain.impl.Domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainTest {

    @Test
    public void indexOfElement_elementNotFound() {
        IDomain sd = Domain.intRange(1, 4);
        Assertions.assertEquals(-1, sd.indexOfElement(DomainElement.of(5)));

        IDomain cd = Domain.combine(Domain.intRange(0, 2), Domain.intRange(0, 3));
        Assertions.assertEquals(-1, cd.indexOfElement(DomainElement.of(0, 4)));
    }

    @Test
    public void indexOfElement_elementFound() {
        IDomain sd = Domain.intRange(1, 4);
        Assertions.assertEquals(1, sd.indexOfElement(DomainElement.of(2)));

        IDomain cd = Domain.combine(Domain.intRange(0, 2), Domain.intRange(0, 3));
        Assertions.assertEquals(5, cd.indexOfElement(DomainElement.of(1, 2)));
    }
}