package hr.fer.zemris.nenr.domain;

public interface IDomain extends Iterable<DomainElement> {

    int getCardinality();

    IDomain getComponent(int i);

    int getNumberOfComponents();

    int indexOfElement(DomainElement element);

    DomainElement elementForIndex(int index);
}
