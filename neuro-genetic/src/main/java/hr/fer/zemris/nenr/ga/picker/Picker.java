package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.GASolution;

import java.util.List;

public interface Picker<T extends GASolution<?>> {

    void configure(List<T> instances);

    int pickOne();
}
