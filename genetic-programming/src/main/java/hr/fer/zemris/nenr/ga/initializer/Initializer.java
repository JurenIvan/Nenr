package hr.fer.zemris.nenr.ga.initializer;

import java.util.List;

public interface Initializer<T> {

    List<T> initialize();
}
