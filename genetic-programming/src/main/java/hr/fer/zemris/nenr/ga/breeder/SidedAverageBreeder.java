package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.Instance;

public class SidedAverageBreeder implements Breeder<Instance> {

    private final double sidedToBetterFactor;

    public SidedAverageBreeder(double sidedToBetterFactor) {
        this.sidedToBetterFactor = sidedToBetterFactor;
    }

    @Override
    public Instance mate(Instance father, Instance mother) {
        double[] better;
        double[] worse;
        double[] chromosome = new double[father.getChromosomes().length];

        if (father.getFitness() < mother.getFitness()) {
            better = father.getChromosomes();
            worse = mother.getChromosomes();
        } else {
            better = mother.getChromosomes();
            worse = father.getChromosomes();
        }

        for (int i = 0; i < father.getChromosomes().length; i++)
            chromosome[i] = ((1 + sidedToBetterFactor) * better[i] + (1 - sidedToBetterFactor) * worse[i]) / 2;

        return new Instance(chromosome);
    }
}
