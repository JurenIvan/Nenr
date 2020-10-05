package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.Objects;

public class MutableFuzzySet implements IFuzzySet {

    private final double[] memberships;
    private final IDomain domain;

    public MutableFuzzySet(IDomain domain) {
        Objects.requireNonNull(domain);

        this.domain = domain;
        memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        Objects.requireNonNull(element);
        return memberships[domain.indexOfElement(element)];
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        Objects.requireNonNull(element);
        memberships[domain.indexOfElement(element)] = value;
        return this;
    }
}
