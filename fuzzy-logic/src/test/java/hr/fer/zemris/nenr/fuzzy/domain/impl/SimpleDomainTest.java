package hr.fer.zemris.nenr.fuzzy.domain.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleDomainTest {

    @Test
    public void getFirst() {
        assertEquals(2, new SimpleDomain(2, 20).getFirst());
        assertEquals(3, new SimpleDomain(3, 40).getFirst());
    }

    @Test
    public void getLast() {
        assertEquals(20, new SimpleDomain(2, 20).getLast());
        assertEquals(40, new SimpleDomain(3, 40).getLast());
    }
}