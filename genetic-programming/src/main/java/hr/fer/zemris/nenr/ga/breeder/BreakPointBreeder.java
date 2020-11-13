package hr.fer.zemris.nenr.ga.breeder;

import hr.fer.zemris.nenr.ga.domain.Instance;

public class BreakPointBreeder implements Breeder<Instance> {

    @Override
    public Instance mate(Instance father, Instance mother) {
        double[] chromosome = new double[father.getChromosomes().length];

        int c = (int) Math.floor((Math.random() * father.getChromosomes().length));
        for (int i = 0; i < father.getChromosomes().length; i++)
            chromosome[i] = i < c ? father.getChromosomes()[i] : mother.getChromosomes()[i];

        return new Instance(chromosome);
    }
}
