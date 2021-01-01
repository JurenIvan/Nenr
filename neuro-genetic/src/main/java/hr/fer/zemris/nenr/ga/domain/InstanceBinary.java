package hr.fer.zemris.nenr.ga.domain;

public class InstanceBinary implements GASolution<boolean[][]> {

    private final boolean[][] chromosomes;
    private double fitness;

    public InstanceBinary(boolean[][] chromosomes) {
        this.chromosomes = chromosomes;
        this.fitness = -1;
    }

    public boolean[][] getChromosomes() {
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chromosomes.length; i++) {
            sb.append('\n').append(i).append("->");
            for (int j = 0; j < chromosomes[0].length; j++) {
                sb.append(chromosomes[i][j] ? "1" : "0");
            }
        }

        return "Instance{cromosomes=" + sb.toString() + ", fitness=" + fitness + '}';
    }
}
