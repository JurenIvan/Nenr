package hr.fer.zemris.nenr.fuzzy.relations;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Double.MIN_VALUE;

public class Relations {

    private Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        if (relation.getDomain().getNumberOfComponents() != 2)
            return false;

        List<DomainElement> d1Elements = new ArrayList<>();
        relation.getDomain().getComponent(0).forEach(d1Elements::add);
        List<DomainElement> d2Elements = new ArrayList<>();
        relation.getDomain().getComponent(1).forEach(d2Elements::add);

        if (!d1Elements.stream().allMatch(d2Elements::remove))
            return false;

        return d2Elements.size() == 0;
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        if (!isUtimesURelation(relation))
            return false;

        int[] values = new int[2];
        var domainElement = DomainElement.of(values);

        for (var element : relation.getDomain()) {
            values[0] = element.getComponentValue(1);
            values[1] = element.getComponentValue(0);
            if (relation.getValueAt(element) != relation.getValueAt(domainElement))
                return false;
        }
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        if (!isUtimesURelation(relation))
            return false;

        int[] values = new int[2];
        var domainElement = DomainElement.of(values);

        Iterator<DomainElement> iterator1 = relation.getDomain().getComponent(0).iterator();
        Iterator<DomainElement> iterator2 = relation.getDomain().getComponent(1).iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            values[0] = iterator1.next().getComponentValue(0);
            values[1] = iterator2.next().getComponentValue(0);
            if (relation.getValueAt(domainElement) != 1.0) return false;
        }

        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        if (!isUtimesURelation(relation))
            return false;

        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet relation1, IFuzzySet relation2) {
        var f1 = (SimpleDomain) relation1.getDomain().getComponent(0);  //redci
        var f2 = (SimpleDomain) relation1.getDomain().getComponent(1);  //stupci
        var s1 = (SimpleDomain) relation2.getDomain().getComponent(0);  //redci
        var s2 = (SimpleDomain) relation2.getDomain().getComponent(1);  //stupci
        if (!f2.equals(s1)) throw new IllegalArgumentException();

        var r3 = new MutableFuzzySet(Domain.combine(f1, s2));

        for (var e1 : f1) {
            for (var e2 : s2) {
                double currentMax = MIN_VALUE;
                for (var e3 : f2) {
                    currentMax = Math.max(currentMax, Math.min(
                            relation1.getValueAt(DomainElement.of(e1.getComponentValue(0), e3.getComponentValue(0))),
                            relation2.getValueAt(DomainElement.of(e3.getComponentValue(0), e2.getComponentValue(0)))));
                }
                r3.set(DomainElement.of(e1.getComponentValue(0), e2.getComponentValue(0)), currentMax);
            }
        }

        return r3;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return Relations.isSymmetric(relation) && Relations.isReflexive(relation) && Relations.isMaxMinTransitive(relation);
    }
}
