package hr.fer.zemris.nenr.fuzzy.domain;

import hr.fer.zemris.nenr.fuzzy.domain.impl.CompositeDomain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompositeDomainTest {

    @Test
    public void compositionOfSimpleDomain() {
        IDomain sd1 = new SimpleDomain(10, 12);
        IDomain sd2 = new SimpleDomain(0, 3);

        IDomain cd = Domain.combine(sd1, sd2);

        Assertions.assertEquals(DomainElement.of(10, 0), cd.elementForIndex(0));
        Assertions.assertEquals(DomainElement.of(10, 1), cd.elementForIndex(1));
        Assertions.assertEquals(DomainElement.of(10, 2), cd.elementForIndex(2));
        Assertions.assertEquals(DomainElement.of(11, 0), cd.elementForIndex(3));
        Assertions.assertEquals(DomainElement.of(11, 1), cd.elementForIndex(4));
        Assertions.assertEquals(DomainElement.of(11, 2), cd.elementForIndex(5));
    }

    @Test
    public void compositionOfSimpleDomains_checkCardinality() {
        IDomain sd1 = new SimpleDomain(10, 12);
        IDomain sd2 = new SimpleDomain(0, 3);
        IDomain cd = Domain.combine(sd1, sd2);

        Assertions.assertEquals(6, cd.getCardinality());
    }

    @Test
    public void compositionOfCompositionDomain() {
        IDomain sd1 = new SimpleDomain(100, 102);
        IDomain sd2 = new SimpleDomain(10, 12);
        IDomain sd3 = new SimpleDomain(0, 2);
        IDomain cd = new CompositeDomain(sd1, new CompositeDomain(sd2, sd3));

        Assertions.assertEquals(DomainElement.of(100, 10, 0), cd.elementForIndex(0));
        Assertions.assertEquals(DomainElement.of(100, 10, 1), cd.elementForIndex(1));
        Assertions.assertEquals(DomainElement.of(100, 11, 0), cd.elementForIndex(2));
        Assertions.assertEquals(DomainElement.of(100, 11, 1), cd.elementForIndex(3));
        Assertions.assertEquals(DomainElement.of(101, 10, 0), cd.elementForIndex(4));
        Assertions.assertEquals(DomainElement.of(101, 10, 1), cd.elementForIndex(5));
        Assertions.assertEquals(DomainElement.of(101, 11, 0), cd.elementForIndex(6));
        Assertions.assertEquals(DomainElement.of(101, 11, 1), cd.elementForIndex(7));
    }

    @Test
    public void compositionOfCompositionDomain_checkCardinality() {
        IDomain sd1 = new SimpleDomain(100, 102);
        IDomain sd2 = new SimpleDomain(10, 12);
        IDomain sd3 = new SimpleDomain(0, 2);
        IDomain cd = new CompositeDomain(sd1, new CompositeDomain(sd2, sd3));

        Assertions.assertEquals(8, cd.getCardinality());
    }
}