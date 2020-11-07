package hr.fer.zemris.nenr.ga.mutator;

import hr.fer.zemris.nenr.ga.Instance;

import java.util.Random;

public class NormalNoiseMutator implements Mutator<Instance> {

    private final double mean;
    private final double deviation;
    private final double mutationChance;
    private final Random random;

    public NormalNoiseMutator(double deviation, double mutationChance) {
        this(mutationChance, deviation, 0);
    }

    public NormalNoiseMutator(double mutationChance, double deviation, double mean) {
        this.random = new Random();
        this.mean = mean;
        this.deviation = deviation;
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(Instance instance) {
        for (int i = 0; i < instance.getCromosomes().length; i++)
            if (Math.random() < mutationChance)
                instance.getCromosomes()[i] += mean + random.nextGaussian() * deviation;
    }
}
