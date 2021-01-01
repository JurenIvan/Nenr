package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.InstanceDouble;

public class BreakPointBreeder implements Breeder<InstanceDouble> {

    @Override
    public InstanceDouble mate(InstanceDouble father, InstanceDouble mother) {
        double[] chromosome = new double[father.getChromosomes().length];

        int c = (int) Math.floor((Math.random() * father.getChromosomes().length));
        for (int i = 0; i < father.getChromosomes().length; i++)
            chromosome[i] = i < c ? father.getChromosomes()[i] : mother.getChromosomes()[i];

        return new InstanceDouble(chromosome);
    }
}
