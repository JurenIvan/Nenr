package hr.fer.zemris.nenr.geneticalgorithm.picker;

import hr.fer.zemris.nenr.geneticalgorithm.domain.GASolution;

import java.util.List;

public interface Picker<T extends GASolution<?>> {

    void configure(List<T> instances);

    int pickOne();
}
