package hr.fer.zemris.nenr.ga.mutator;

import hr.fer.zemris.nenr.ga.domain.InstanceDouble;

public class FunkyMutator implements Mutator<InstanceDouble> {

    private final double mutationChance;

    public FunkyMutator(double mutationChance) {
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(InstanceDouble instance) {
        int chromosomeLength = instance.getChromosomes().length;
        for (int i = 0; i < chromosomeLength; i++)
            if (Math.random() < mutationChance)
                instance.getChromosomes()[i] = instance.getChromosomes()[(int) Math.round(Math.random() * chromosomeLength)];
    }
}
