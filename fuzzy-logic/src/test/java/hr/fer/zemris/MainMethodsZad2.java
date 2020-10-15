package hr.fer.zemris;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.relations.Relations;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MainMethodsZad2 {

    @Test
    @Disabled
    public void primjer1() {
        IDomain u = Domain.intRange(1, 6); // {1,2,3,4,5}
        IDomain u2 = Domain.combine(u, u);
        IFuzzySet r1 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.5);
        IFuzzySet r2 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.1);
        IFuzzySet r3 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 0.3)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(1, 2), 0.6)
                .set(DomainElement.of(2, 1), 0.6)
                .set(DomainElement.of(2, 3), 0.7)
                .set(DomainElement.of(3, 2), 0.7)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.5);
        IFuzzySet r4 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(1, 2), 0.4)
                .set(DomainElement.of(2, 1), 0.4)
                .set(DomainElement.of(2, 3), 0.5)
                .set(DomainElement.of(3, 2), 0.5)
                .set(DomainElement.of(1, 3), 0.4)
                .set(DomainElement.of(3, 1), 0.4);
        boolean test1 = Relations.isUtimesURelation(r1);
        System.out.println("r1 je definiran nad UxU? " + test1);
        boolean test2 = Relations.isSymmetric(r1);
        System.out.println("r1 je simetrična? " + test2);
        boolean test3 = Relations.isSymmetric(r2);
        System.out.println("r2 je simetrična? " + test3);
        boolean test4 = Relations.isReflexive(r1);
        System.out.println("r1 je refleksivna? " + test4);
        boolean test5 = Relations.isReflexive(r3);
        System.out.println("r3 je refleksivna? " + test5);
        boolean test6 = Relations.isMaxMinTransitive(r3);
        System.out.println("r3 je max-min tranzitivna? " + test6);
        boolean test7 = Relations.isMaxMinTransitive(r4);
        System.out.println("r4 je max-min tranzitivna? " + test7);
    }


    @Test
    @Disabled
    public void primjer2() {
        IDomain u1 = Domain.intRange(1, 5); // {1,2,3,4}
        IDomain u2 = Domain.intRange(1, 4); // {1,2,3}
        IDomain u3 = Domain.intRange(1, 5); // {1,2,3,4}
        IFuzzySet r1 = new MutableFuzzySet(Domain.combine(u1, u2))
                .set(DomainElement.of(1, 1), 0.3)
                .set(DomainElement.of(1, 2), 1)
                .set(DomainElement.of(3, 3), 0.5)
                .set(DomainElement.of(4, 3), 0.5);
        IFuzzySet r2 = new MutableFuzzySet(Domain.combine(u2, u3))
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 1), 0.5)
                .set(DomainElement.of(2, 2), 0.7)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(3, 4), 0.4);
        IFuzzySet r1r2 = Relations.compositionOfBinaryRelations(r1, r2);
        for (DomainElement e : r1r2.getDomain()) {
            System.out.println("mu(" + e + ")=" + r1r2.getValueAt(e));
        }
    }

    @Test
    @Disabled
    public void primjer3() {
        IDomain u = Domain.intRange(1, 5); // {1,2,3,4}
        IFuzzySet r = new MutableFuzzySet(Domain.combine(u, u))
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
        System.out.println(
                "Početna relacija je neizrazita relacija ekvivalencije? " +
                        Relations.isFuzzyEquivalence(r2));
        System.out.println();
        for (int i = 1; i <= 3; i++) {
            r2 = Relations.compositionOfBinaryRelations(r2, r);
            System.out.println(
                    "Broj odrađenih kompozicija: " + i + ". Relacija je:");
            for (DomainElement e : r2.getDomain()) {
                System.out.println("mu(" + e + ")=" + r2.getValueAt(e));
            }
            System.out.println(
                    "Ova relacija je neizrazita relacija ekvivalencije? " +
                            Relations.isFuzzyEquivalence(r2));
            System.out.println();
        }
    }
}