package hr.fer.zemris.nenr.domain.impl;

import hr.fer.zemris.nenr.domain.DomainElement;
import hr.fer.zemris.nenr.domain.IDomain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

public class CompositeDomain extends Domain {

    private final IDomain[] domains;

    public CompositeDomain(IDomain... domains) {
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
        for (var e : domains) product *= e.getCardinality();
        return product;
    }

    @Override
    public int getNumberOfComponents() {
        int sum = 0;
        for (var domain : domains)
            sum += domain.getNumberOfComponents();
        return sum;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {

            private final List<Iterator<DomainElement>> iterators;
            private final DomainElement[] values;
            private boolean hasNext;

            {
                iterators = Arrays.stream(domains).map(Iterable::iterator).collect(toList());
                values = new DomainElement[iterators.size()];
                for (int i = 0; i < iterators.size(); i++) {
                    values[i] = iterators.get(i).next();
                }
                hasNext = true;
            }

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public DomainElement next() {
                if (!hasNext) throw new NoSuchElementException();

                DomainElement elementToReturn = composeCurrentElement();
                prepareNext();
                return elementToReturn;
            }

            private void prepareNext() {
                for (int i = iterators.size() - 1; i >= 0; i--) {
                    if (iterators.get(i).hasNext()) {
                        values[i] = iterators.get(i).next();
                        hasNext = true;
                        return;
                    } else {
                        iterators.set(i, getComponent(i).iterator());
                        values[i] = iterators.get(i).next();
                    }
                }
                hasNext = false;
            }

            private DomainElement composeCurrentElement() {
                int[] returnArr = new int[getNumberOfComponents()];
                int counter = 0;
                for (DomainElement value : values) {
                    for (int j = 0; j < value.getNumberOfComponents(); j++) {
                        returnArr[counter++] = value.getComponentValue(j);
                    }
                }
                return DomainElement.of(returnArr);
            }
        };
    }
}
