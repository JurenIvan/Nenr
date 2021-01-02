package hr.fer.zemris.nenr.geneticalgorithm.selection;

import java.util.List;

public interface Selection<T> {

    void doSelection(List<T> population);
}
