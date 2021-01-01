package hr.fer.zemris.nenr.ga.domain;

public interface GASolution<T> {

    double getFitness();

    void setFitness(double fitness);

    T getChromosomes();
}
