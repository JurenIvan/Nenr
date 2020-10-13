package hr.fer.zemris.nenr.fuzzy.relations;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.Iterator;

public class Relations {

    private Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        return relation.getDomain().getNumberOfComponents() == 2 && relation.getDomain().getComponent(0).equals(relation.getDomain().getComponent(1));
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

        for (var x : relation.getDomain().getComponent(0)) {
            for (var z : relation.getDomain().getComponent(0)) {
                double value = relation.getValueAt(DomainElement.of(x.getComponentValue(0), z.getComponentValue(0)));
                double max = 0;
                for (var y : relation.getDomain().getComponent(0)) {
                    var de1 = DomainElement.of(x.getComponentValue(0), y.getComponentValue(0));
                    var de2 = DomainElement.of(y.getComponentValue(0), z.getComponentValue(0));
                    max = Math.max(max, Math.min(relation.getValueAt(de1), relation.getValueAt(de2)));
                }
                if (value < max)
                    return false;
            }
        }
        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet relation1, IFuzzySet relation2) {
        var f1 = (SimpleDomain) relation1.getDomain().getComponent(0);
        var f2 = (SimpleDomain) relation1.getDomain().getComponent(1);
        var s1 = (SimpleDomain) relation2.getDomain().getComponent(0);
        var s2 = (SimpleDomain) relation2.getDomain().getComponent(1);
        if (!f2.equals(s1)) throw new IllegalArgumentException();

        var r3 = new MutableFuzzySet(Domain.combine(f1, s2));

        for (var e1 : f1) {
            for (var e2 : s2) {
                double currentMax = 0;
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
        return isSymmetric(relation) && isReflexive(relation) && isMaxMinTransitive(relation);
    }
}
