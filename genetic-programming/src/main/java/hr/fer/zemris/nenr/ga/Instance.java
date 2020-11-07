package hr.fer.zemris.nenr.ga;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class Instance {

    private final double[] cromosomes;
    private double fitness;

    public Instance(double... cromosomes) {
        this.cromosomes = cromosomes;
        this.fitness = -1;
    }

    public double[] getCromosomes() {
        return cromosomes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "cromosomes=" + Arrays.stream(cromosomes).mapToObj(e -> String.format("%5.5f", e)).collect(joining(",")) +
                ", fitness=" + fitness + '}';
    }
}
