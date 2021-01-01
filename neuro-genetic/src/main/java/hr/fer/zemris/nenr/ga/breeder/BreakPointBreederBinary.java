package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

public class BreakPointBreederBinary implements Breeder<InstanceBinary> {
    @Override
    public InstanceBinary mate(InstanceBinary father, InstanceBinary mother) {
        int dim = father.getChromosomes().length;
        boolean[][] result = new boolean[dim][];
        for (int dimOrder = 0; dimOrder < dim; dimOrder++) {
            boolean[] chromosome = new boolean[father.getChromosomes()[dimOrder].length];

            var fatherChromosome = father.getChromosomes()[dimOrder];
            var motherChromosome = mother.getChromosomes()[dimOrder];

            int c = (int) Math.floor((Math.random() * father.getChromosomes().length));
            for (int i = 0; i < father.getChromosomes().length; i++)
                chromosome[i] = i < c ? fatherChromosome[i] : motherChromosome[i];
            result[dimOrder] = chromosome;
        }
        return new InstanceBinary(result);
    }
}
