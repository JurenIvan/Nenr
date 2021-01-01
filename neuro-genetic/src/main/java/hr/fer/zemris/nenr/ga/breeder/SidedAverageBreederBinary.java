package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

public class SidedAverageBreederBinary implements Breeder<InstanceBinary> {

    private final double sidedToBetterFactor;

    public SidedAverageBreederBinary(double sidedToBetterFactor) {
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

        for (int i = 0; i < father.getChromosomes().length; i++) {
            boolean[] r = new boolean[father.getChromosomes()[i].length];
            chromosome[i] = r;
            for (int j = 0; j < father.getChromosomes()[i].length; j++) {
                r[j] = Math.random() < sidedToBetterFactor ? better[i][j] : worse[i][j];
            }
        }
        return new InstanceBinary(chromosome);
    }
}
