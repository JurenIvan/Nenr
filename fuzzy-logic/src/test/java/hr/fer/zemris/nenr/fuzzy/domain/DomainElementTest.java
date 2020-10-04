package hr.fer.zemris.nenr.fuzzy.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainElementTest {

    @Test
    public void nullValuesInConstructor_exceptionIsThrown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DomainElement.of(null));
    }
}