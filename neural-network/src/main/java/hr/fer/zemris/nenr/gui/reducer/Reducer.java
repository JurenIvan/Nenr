package hr.fer.zemris.nenr.gui.reducer;

import java.util.List;

public interface Reducer<T> {

    double[] reduce(List<T> points);
}
