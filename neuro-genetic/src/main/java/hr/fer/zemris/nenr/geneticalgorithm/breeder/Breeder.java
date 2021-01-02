package hr.fer.zemris.nenr.geneticalgorithm.breeder;

public interface Breeder<T> {

    T mate(T father, T mother);
}
