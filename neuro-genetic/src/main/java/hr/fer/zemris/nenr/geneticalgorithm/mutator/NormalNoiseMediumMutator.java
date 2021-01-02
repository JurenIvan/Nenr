package hr.fer.zemris.nenr.geneticalgorithm.mutator;

import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;

import java.util.Random;

import static java.lang.Math.random;

public class NormalNoiseMediumMutator implements Mutator<InstanceDouble> {

    private final double mean;
    private final double deviation;
    private final double mutationChance;
    private final Random random;

    public NormalNoiseMediumMutator(double deviation, double mutationChance) {
        this(mutationChance, deviation, 0);
    }

    public NormalNoiseMediumMutator(double mutationChance, double deviation, double mean) {
        this.random = new Random();
        this.mean = mean;
        this.deviation = deviation;
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(InstanceDouble instance) {
        if (random() < mutationChance)
            for (int i = 0; i < instance.getChromosomes().length; i++)
                instance.getChromosomes()[i] += mean + random.nextGaussian() * deviation;
    }
}
