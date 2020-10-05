package hr.fer.zemris.nenr.fuzzy.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainElementTest {

    @Test
    public void nullValuesInConstructor_exceptionIsThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> DomainElement.of((int[]) null));
    }

    @Test
    void testToString() {
        Assertions.assertEquals("1",DomainElement.of(1).toString());
        Assertions.assertEquals("(1,2)",DomainElement.of(1,2).toString());
        Assertions.assertEquals("(1,2,3)",DomainElement.of(1,2,3).toString());
    }
}