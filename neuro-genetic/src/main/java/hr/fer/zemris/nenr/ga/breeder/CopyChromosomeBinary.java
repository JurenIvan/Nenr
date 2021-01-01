package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

public class CopyChromosomeBinary implements Breeder<InstanceBinary> {

    private final double sidedToBetterFactor;

    public CopyChromosomeBinary(double sidedToBetterFactor) {
        this.sidedToBetterFactor = sidedToBetterFactor;
    }

    @Override
    public InstanceBinary mate(InstanceBinary father, InstanceBinary mother) {
        boolean[][] chromosome = new boolean[father.getChromosomes().length][];
        boolean[][] better;
        boolean[][] worse;

        if (father.getFitness() < mother.getFitness()) {
            better = father.getChromosomes();
            worse = mother.getChromosomes();
        } else {
            better = mother.getChromosomes();
            worse = father.getChromosomes();
        }

        for (int i = 0; i < father.getChromosomes().length; i++)
            chromosome[i] = Math.random() < sidedToBetterFactor ? better[i] : worse[i];

        return new InstanceBinary(chromosome);
    }
}
