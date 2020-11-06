package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractFuzzySet implements IFuzzySet {

    private final IDomain domain;

    public AbstractFuzzySet(IDomain domain) {
        this.domain = domain;
    }

    public String toString() {
        return "MutableFuzzySet{\n" +
                StreamSupport.stream(getDomain().spliterator(), false)
                        .map(e -> e + " : " + getValueAt(e))
                        .collect(Collectors.joining("\n"))
                + '}';
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    public abstract double getValueAt(DomainElement element);
}
