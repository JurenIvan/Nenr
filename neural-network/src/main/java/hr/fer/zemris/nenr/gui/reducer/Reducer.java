package hr.fer.zemris.nenr.gui.reducer;

import java.util.List;

public interface Reducer<T> {

    List<T> reduce(List<T> points);
}
