package hr.fer.zemris.nenr.domain.impl;

import hr.fer.zemris.nenr.domain.DomainElement;
import hr.fer.zemris.nenr.domain.IDomain;

import java.util.Iterator;

public abstract class Domain implements IDomain {

    public static IDomain intRange(int from, int to) {
        return new SimpleDomain(from, to);
    }

    public static IDomain combine(IDomain first, IDomain second) {
        return new CompositeDomain(first, second);
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
