package hr.fer.zemris.nenr.fuzzy.relations;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.nenr.fuzzy.domain.DomainElement.of;
import static hr.fer.zemris.nenr.fuzzy.domain.impl.Domain.combine;
import static hr.fer.zemris.nenr.fuzzy.domain.impl.Domain.intRange;
import static org.junit.jupiter.api.Assertions.*;

class RelationsTest {
    private static final IDomain DOMAIN = combine(intRange(1, 6), intRange(1, 6));

    private static final IFuzzySet R1 = new MutableFuzzySet(DOMAIN)
            .set(of(1, 1), 1)
            .set(of(2, 2), 1)
            .set(of(3, 3), 1)
            .set(of(4, 4), 1)
            .set(of(5, 5), 1)
            .set(of(3, 1), 0.5)
            .set(of(1, 3), 0.5);

    private static final IFuzzySet R2 = new MutableFuzzySet(DOMAIN)
            .set(of(1, 1), 1)
            .set(of(2, 2), 1)
            .set(of(3, 3), 1)
            .set(of(4, 4), 1)
            .set(of(5, 5), 1)
            .set(of(3, 1), 0.5)
            .set(of(1, 3), 0.1);

    private static final IFuzzySet R3 = new MutableFuzzySet(DOMAIN)
            .set(of(1, 1), 1)
            .set(of(2, 2), 1)
            .set(of(3, 3), 0.3)
            .set(of(4, 4), 1)
            .set(of(5, 5), 1)
            .set(of(1, 2), 0.6)
            .set(of(2, 1), 0.6)
            .set(of(2, 3), 0.7)
            .set(of(3, 2), 0.7)
            .set(of(3, 1), 0.5)
            .set(of(1, 3), 0.5);

    private static final IFuzzySet R4 = new MutableFuzzySet(DOMAIN)
            .set(of(1, 1), 1)
            .set(of(2, 2), 1)
            .set(of(3, 3), 1)
            .set(of(4, 4), 1)
            .set(of(5, 5), 1)
            .set(of(1, 2), 0.4)
            .set(of(2, 1), 0.4)
            .set(of(2, 3), 0.5)
            .set(of(3, 2), 0.5)
            .set(of(1, 3), 0.4)
            .set(of(3, 1), 0.4);


    @Test
    void isUtimesURelation() {
        assertTrue(Relations.isUtimesURelation(R1));
    }

    @Test
    void isSymmetric() {
        assertTrue(Relations.isSymmetric(R1));
        assertFalse(Relations.isSymmetric(R2));
    }

    @Test
    void isReflexive() {
        assertTrue(Relations.isReflexive(R1));
        assertFalse(Relations.isReflexive(R3));
    }

    @Test
    void isMaxMinTransitive() {
        assertFalse(Relations.isMaxMinTransitive(R3));
        assertTrue(Relations.isMaxMinTransitive(R4));
    }

    @Test
    void compositionOfBinaryRelations() {
        IDomain u1 = intRange(1, 5); // {1,2,3,4}
        IDomain u2 = intRange(1, 4); // {1,2,3}
        IDomain u3 = intRange(1, 5); // {1,2,3,4}
        IFuzzySet r1 = new MutableFuzzySet(combine(u1, u2))
                .set(of(1, 1), 0.3)
                .set(of(1, 2), 1)
                .set(of(3, 3), 0.5)
                .set(of(4, 3), 0.5);
        IFuzzySet r2 = new MutableFuzzySet(combine(u2, u3))
                .set(of(1, 1), 1)
                .set(of(2, 1), 0.5)
                .set(of(2, 2), 0.7)
                .set(of(3, 3), 1)
                .set(of(3, 4), 0.4);
        IFuzzySet r1r2 = Relations.compositionOfBinaryRelations(r1, r2);

        assertEquals(0.5, r1r2.getValueAt(DomainElement.of(1, 1)), 1e-8);
        assertEquals(0.7, r1r2.getValueAt(DomainElement.of(1, 2)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(1, 3)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(1, 4)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(2, 1)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(2, 2)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(2, 3)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(2, 4)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(3, 1)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(3, 2)), 1e-8);
        assertEquals(0.5, r1r2.getValueAt(DomainElement.of(3, 3)), 1e-8);
        assertEquals(0.4, r1r2.getValueAt(DomainElement.of(3, 4)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(4, 1)), 1e-8);
        assertEquals(0.0, r1r2.getValueAt(DomainElement.of(4, 2)), 1e-8);
        assertEquals(0.5, r1r2.getValueAt(DomainElement.of(4, 3)), 1e-8);
        assertEquals(0.4, r1r2.getValueAt(DomainElement.of(4, 4)), 1e-8);
    }


    @Test
    void isFuzzyEquivalence() {
        IDomain u = intRange(1, 5); // {1,2,3,4}
        IFuzzySet r = new MutableFuzzySet(combine(u, u))
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(1, 2), 0.3)
                .set(DomainElement.of(2, 1), 0.3)
                .set(DomainElement.of(2, 3), 0.5)
                .set(DomainElement.of(3, 2), 0.5)
                .set(DomainElement.of(3, 4), 0.2)
                .set(DomainElement.of(4, 3), 0.2);

        IFuzzySet r2 = r;
        assertFalse(Relations.isFuzzyEquivalence(r2));

        r2 = Relations.compositionOfBinaryRelations(r2, r);
        assertFalse(Relations.isFuzzyEquivalence(r2));
        assertEquals(1.0, r2.getValueAt(DomainElement.of(1, 1)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 2)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 3)), 1e-8);
        assertEquals(0.0, r2.getValueAt(DomainElement.of(1, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(2, 1)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(2, 2)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(2, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(2, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(3, 1)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(3, 2)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(3, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(3, 4)), 1e-8);
        assertEquals(0.0, r2.getValueAt(DomainElement.of(4, 1)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 2)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 3)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(4, 4)), 1e-8);

        r2 = Relations.compositionOfBinaryRelations(r2, r);
        assertTrue(Relations.isFuzzyEquivalence(r2));
        assertEquals(1.0, r2.getValueAt(DomainElement.of(1, 1)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 2)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(1, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(2, 1)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(2, 2)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(2, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(2, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(3, 1)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(3, 2)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(3, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(3, 4)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 1)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 2)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 3)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(4, 4)), 1e-8);

        r2 = Relations.compositionOfBinaryRelations(r2, r);
        assertTrue(Relations.isFuzzyEquivalence(r2));
        assertEquals(1.0, r2.getValueAt(DomainElement.of(1, 1)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 2)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(1, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(1, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(2, 1)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(2, 2)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(2, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(2, 4)), 1e-8);
        assertEquals(0.3, r2.getValueAt(DomainElement.of(3, 1)), 1e-8);
        assertEquals(0.5, r2.getValueAt(DomainElement.of(3, 2)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(3, 3)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(3, 4)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 1)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 2)), 1e-8);
        assertEquals(0.2, r2.getValueAt(DomainElement.of(4, 3)), 1e-8);
        assertEquals(1.0, r2.getValueAt(DomainElement.of(4, 4)), 1e-8);
    }

    @Test
    void testIsSymmetric() {
        IDomain DOMAIN = combine(intRange(1, 6), intRange(1, 3));
        assertFalse(Relations.isSymmetric(new MutableFuzzySet(DOMAIN)));
    }

    @Test
    void testIsReflexive() {
        IDomain DOMAIN = combine(intRange(1, 6), intRange(1, 3));
        assertFalse(Relations.isReflexive(new MutableFuzzySet(DOMAIN)));
    }

    @Test
    void testIsMaxMinTransitive() {
        IDomain DOMAIN = combine(intRange(1, 6), intRange(1, 3));
        assertFalse(Relations.isMaxMinTransitive(new MutableFuzzySet(DOMAIN)));
    }
}