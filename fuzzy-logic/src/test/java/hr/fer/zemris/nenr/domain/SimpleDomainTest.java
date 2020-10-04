package hr.fer.zemris.nenr.domain;

import hr.fer.zemris.nenr.domain.impl.SimpleDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleDomainTest {

    @Test
    public void testSimpleDomain() {
        SimpleDomain sd = new SimpleDomain(0, 4);

        Assertions.assertEquals(DomainElement.of(0), sd.elementForIndex(0));
        Assertions.assertEquals(DomainElement.of(1), sd.elementForIndex(1));
        Assertions.assertEquals(DomainElement.of(2), sd.elementForIndex(2));
        Assertions.assertEquals(DomainElement.of(3), sd.elementForIndex(3));
    }

    @Test
    public void simpleDomain_argumentsInverted_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SimpleDomain(4, 0));
    }

    @Test
    public void simpleDomain_elementForIndexOutOfRange_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SimpleDomain(0, 4).elementForIndex(20));
    }

}