package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.unaryfunctions.IIntUnaryFunction;

import java.util.Objects;

public class CalculatedFuzzySet implements IFuzzySet {

    private final IDomain domain;
    private final IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction function) {
        Objects.requireNonNull(domain);
        Objects.requireNonNull(function);

        this.domain = domain;
        this.function = function;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        Objects.requireNonNull(element);
        return function.valueAt(domain.indexOfElement(element));
    }
}
