package hr.fer.zemris.nenr.ga.mutator;

import hr.fer.zemris.nenr.ga.Instance;

public class FunkyMutator implements Mutator<Instance> {

    private final double mutationChance;

    public FunkyMutator(double mutationChance) {
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(Instance instance) {
        int chromosomeLength = instance.getCromosomes().length;
        for (int i = 0; i < chromosomeLength; i++)
            if (Math.random() < mutationChance)
                instance.getCromosomes()[i] = instance.getCromosomes()[(int) Math.round(Math.random() * chromosomeLength)];
    }
}
