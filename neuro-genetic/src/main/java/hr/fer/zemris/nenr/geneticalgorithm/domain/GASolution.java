package hr.fer.zemris.nenr.geneticalgorithm.domain;

public interface GASolution<T> {

    double getFitness();

    void setFitness(double fitness);

    T getChromosomes();
}
