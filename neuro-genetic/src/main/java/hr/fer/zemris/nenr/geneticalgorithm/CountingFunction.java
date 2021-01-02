package hr.fer.zemris.nenr.geneticalgorithm;

import java.util.function.Function;

public class CountingFunction<IN, OUT> implements Function<IN, OUT> {

    private final Function<IN, OUT> function;
    private int counter = 0;

    public CountingFunction(Function<IN, OUT> function) {
        this.function = function;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public OUT apply(IN in) {
        counter++;
        return function.apply(in);
    }
}
