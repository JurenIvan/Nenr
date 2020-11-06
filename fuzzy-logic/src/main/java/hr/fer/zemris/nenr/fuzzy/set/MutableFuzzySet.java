package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

import java.util.Objects;

public class MutableFuzzySet extends AbstractFuzzySet {

    private final double[] memberships;

    public MutableFuzzySet(IDomain domain) {
        super(domain);
        Objects.requireNonNull(domain);

        memberships = new double[domain.getCardinality()];
    }

    @Override
    public double getValueAt(DomainElement element) {
        Objects.requireNonNull(element);
        return memberships[getDomain().indexOfElement(element)];
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        Objects.requireNonNull(element);
        memberships[getDomain().indexOfElement(element)] = value;
        return this;
    }
}
