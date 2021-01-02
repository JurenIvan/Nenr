package hr.fer.zemris.nenr.geneticalgorithm.initializer;

import java.util.List;

public interface Initializer<T> {

    List<T> initialize();
}
