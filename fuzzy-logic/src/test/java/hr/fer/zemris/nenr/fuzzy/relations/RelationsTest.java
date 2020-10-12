package hr.fer.zemris.nenr.fuzzy.relations;

import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.nenr.fuzzy.domain.DomainElement.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationsTest {
    private static final IDomain DOMAIN = Domain.combine(Domain.intRange(1, 6), Domain.intRange(1, 6));

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
}