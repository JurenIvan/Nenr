package hr.fer.zemris.nenr.ga.breeder;

public interface Breeder<T> {

    T mate(T father, T mother);
}
