package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.domain.IDomain;

public interface IFuzzySet {

    IDomain getDomain();

    double getValueAt(DomainElement element);
}
