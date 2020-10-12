package hr.fer.zemris.nenr.fuzzy.domain.impl;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CompositeDomain extends Domain {

    private final SimpleDomain[] domains;

    public CompositeDomain(SimpleDomain... domains) {
        Objects.requireNonNull(domains);
        this.domains = domains;
    }

    @Override
    public IDomain getComponent(int i) {
        return domains[i];
    }

    @Override
    public int getCardinality() {
        if (domains.length == 0) return 0;

        int product = 1;
        for (var e : domains)
            product *= e.getCardinality();

        return product;
    }

    @Override
    public int getNumberOfComponents() {
        return domains.length;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {

            private final int[] currentElement = new int[getNumberOfComponents()];
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public DomainElement next() {
                if (!hasNext) throw new NoSuchElementException();

                DomainElement elementToReturn = composeCurrent();
                prepareNext();
                return elementToReturn;
            }

            private DomainElement composeCurrent() {
                int[] values = new int[currentElement.length];
                for (int i = 0; i < values.length; i++) {
                    values[i] = domains[i].valueForIndex(currentElement[i]);
                }
                return DomainElement.of(values);
            }

            private void prepareNext() {
                for (int i = currentElement.length - 1; i >= 0; i--) {
                    if (domains[i].getCardinality() - 1 > currentElement[i]) {
                        currentElement[i] = currentElement[i] + 1;
                        hasNext = true;
                        return;
                    } else {
                        currentElement[i] = 0;
                    }
                }
                hasNext = false;
            }
        };
    }
}
