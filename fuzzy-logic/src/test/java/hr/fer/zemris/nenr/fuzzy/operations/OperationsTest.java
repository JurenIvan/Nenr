package hr.fer.zemris.nenr.fuzzy.operations;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationsTest {

    @Test
    void unaryOperation() {
        var fuzzySet = new MutableFuzzySet(Domain.intRange(0, 11));
        for (int i = 0; i < 10; i++) {
            fuzzySet.set(DomainElement.of(i), i / 10.0);
        }
        var calculatedSet = Operations.unaryOperation(fuzzySet, Operations.zadehNot());

        for (int i = 0; i < 10; i++) {
            assertEquals((10 - i) / 10.0, calculatedSet.getValueAt(DomainElement.of(i)), 1e-4);
        }
    }

    @Test
    void binaryOperation() {
        var fuzzySetUp = new MutableFuzzySet(Domain.intRange(0, 11));
        var fuzzySetDown = new MutableFuzzySet(Domain.intRange(0, 11));
        for (int i = 0; i < 10; i++) {
            fuzzySetUp.set(DomainElement.of(i), i / 10.0);
            fuzzySetDown.set(DomainElement.of(i), (10 - i) / 10.0);
        }
        var calculatedSet = Operations.binaryOperation(fuzzySetUp, fuzzySetDown, Operations.zadehAnd());

        assertEquals(0.1, calculatedSet.getValueAt(DomainElement.of(1)), 1e-4);
        assertEquals(0.2, calculatedSet.getValueAt(DomainElement.of(2)), 1e-4);
        assertEquals(0.3, calculatedSet.getValueAt(DomainElement.of(3)), 1e-4);
        assertEquals(0.4, calculatedSet.getValueAt(DomainElement.of(4)), 1e-4);
        assertEquals(0.5, calculatedSet.getValueAt(DomainElement.of(5)), 1e-4);
        assertEquals(0.4, calculatedSet.getValueAt(DomainElement.of(6)), 1e-4);
        assertEquals(0.3, calculatedSet.getValueAt(DomainElement.of(7)), 1e-4);
        assertEquals(0.2, calculatedSet.getValueAt(DomainElement.of(8)), 1e-4);
        assertEquals(0.1, calculatedSet.getValueAt(DomainElement.of(9)), 1e-4);

    }

    @Test
    void zadehNot() {
        assertEquals(0.2, Operations.zadehNot().valueAt(0.8), 1e-6);
    }

    @Test
    void zadehAnd() {
        assertEquals(0.2, Operations.zadehAnd().valueAt(0.8, 0.2), 1e-6);
    }

    @Test
    void zadehOr() {
        assertEquals(0.8, Operations.zadehOr().valueAt(0.8, 0.2), 1e-6);
    }

    @Test
    void hamacherTNorm() {
        assertEquals(0.084269, Operations.hamacherTNorm(0.2).valueAt(0.1, 0.6), 1e-4);
    }

    @Test
    void hamacherSNorm() {
        assertEquals(0.62184, Operations.hamacherSNorm(0.2).valueAt(0.1, 0.6), 1e-3);
    }
}