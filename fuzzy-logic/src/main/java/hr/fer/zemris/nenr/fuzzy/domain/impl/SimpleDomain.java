package hr.fer.zemris.nenr.fuzzy.domain.impl;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.Iterator;
import java.util.Objects;

public class SimpleDomain extends Domain {

    private final int first;
    private final int last;

    public SimpleDomain(int start, int end) {
        if (start >= end) throw new IllegalArgumentException("First must be smaller than second");

        this.first = start;
        this.last = end;
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {
            private int current = first;

            @Override
            public boolean hasNext() {
                return current < last;
            }

            @Override
            public DomainElement next() {
                return DomainElement.of(current++);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDomain that = (SimpleDomain) o;

        if (first != that.first) return false;
        return last == that.last;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }

    @Override
    public String toString() {
        return "SimpleDomain{first=" + first + ", last=" + last + '}';
    }

    @Override
    public int getCardinality() {
        return last - first;
    }

    @Override
    public IDomain getComponent(int i) {
        return this;
    }

    @Override
    public int getNumberOfComponents() {
        return 1;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        return DomainElement.of(valueForIndex(index));
    }

    public int valueForIndex(int index) {
        if (first + index > last || index < 0)
            throw new IllegalArgumentException();

        return first + index;
    }
}
