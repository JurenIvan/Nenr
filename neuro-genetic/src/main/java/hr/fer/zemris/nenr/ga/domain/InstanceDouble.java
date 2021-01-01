package hr.fer.zemris.nenr.ga.domain;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class InstanceDouble implements GASolution<double[]> {

    private final double[] chromosomes;
    private double fitness;

    public InstanceDouble(double... chromosomes) {
        this.chromosomes = chromosomes;
        this.fitness = -1;
    }

    public double[] getChromosomes() {
        return chromosomes;
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
                "cromosomes=" + stream(chromosomes).mapToObj(e -> format("%5.5f", e)).collect(joining(",")) +
                ", fitness=" + fitness + '}';
    }
}
