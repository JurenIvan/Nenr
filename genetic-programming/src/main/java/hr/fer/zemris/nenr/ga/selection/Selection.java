package hr.fer.zemris.nenr.ga.selection;

import java.util.List;

public interface Selection<T> {

    void doSelection(List<T> population);
}
