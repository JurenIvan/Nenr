package hr.fer.zemris.nenr.ga.picker;

import java.util.List;

public interface Picker<T> {

    void configure(List<T> instances);

    int pickOne();
}
