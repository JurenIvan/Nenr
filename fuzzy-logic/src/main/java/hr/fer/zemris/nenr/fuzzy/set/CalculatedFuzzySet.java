package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.system.AbstractFuzzySystem;
import hr.fer.zemris.nenr.fuzzy.unaryfunctions.IIntUnaryFunction;

import java.util.Objects;

public class CalculatedFuzzySet extends AbstractFuzzySet {

    private final IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction function) {
        super(domain);
        Objects.requireNonNull(domain);
        Objects.requireNonNull(function);

        this.function = function;
    }

    @Override
    public double getValueAt(DomainElement element) {
        Objects.requireNonNull(element);
        return function.valueAt(getDomain().indexOfElement(element));
    }
}
