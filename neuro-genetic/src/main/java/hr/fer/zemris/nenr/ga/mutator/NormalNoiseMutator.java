package hr.fer.zemris.nenr.ga.mutator;

import hr.fer.zemris.nenr.ga.domain.InstanceDouble;

import java.util.Random;

import static java.lang.Math.random;

public class NormalNoiseMutator implements Mutator<InstanceDouble> {

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
    public void mutate(InstanceDouble instance) {
        for (int i = 0; i < instance.getChromosomes().length; i++)
            if (random() < mutationChance)
                instance.getChromosomes()[i] += mean + random.nextGaussian() * deviation;
    }
}
