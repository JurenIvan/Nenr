package hr.fer.zemris.nenr.fuzzy.domain.impl;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.Iterator;

public abstract class Domain implements IDomain {

    public static IDomain intRange(int from, int to) {
        return new SimpleDomain(from, to);
    }

    public static IDomain combine(IDomain first, IDomain second) {
        int numberOfFirstComponents = first.getNumberOfComponents();
        int numberOfSecondComponents = second.getNumberOfComponents();

        SimpleDomain[] domains = new SimpleDomain[numberOfFirstComponents + numberOfSecondComponents];

        for (int i = 0; i < numberOfFirstComponents; i++)
            domains[i] = (SimpleDomain) first.getComponent(i);
        for (int i = 0; i < numberOfSecondComponents; i++)
            domains[i + numberOfFirstComponents] = (SimpleDomain) second.getComponent(i);

        return new CompositeDomain(domains);
    }

    @Override
    public int indexOfElement(DomainElement element) {
        int index = 0;
        for (var val : this) {
            if (val.equals(element)) return index;
            index++;
        }
        return -1;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        Iterator<DomainElement> iterator = iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}
