package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.Instance;

public class CopyChromosome implements Breeder<Instance> {

    private final double sidedToBetterFactor;

    public CopyChromosome(double sidedToBetterFactor) {
        this.sidedToBetterFactor = sidedToBetterFactor;
    }

    @Override
    public Instance mate(Instance father, Instance mother) {
        double[] chromosome = new double[father.getChromosomes().length];
        double[] better;
        double[] worse;

        if (father.getFitness() < mother.getFitness()) {
            better = father.getChromosomes();
            worse = mother.getChromosomes();
        } else {
            better = mother.getChromosomes();
            worse = father.getChromosomes();
        }

        for (int i = 0; i < father.getChromosomes().length; i++)
            chromosome[i] = Math.random() < sidedToBetterFactor ? better[i] : worse[i];

        return new Instance(chromosome);
    }
}
