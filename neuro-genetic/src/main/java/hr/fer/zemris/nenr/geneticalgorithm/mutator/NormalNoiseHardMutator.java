package hr.fer.zemris.nenr.geneticalgorithm.mutator;

import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;

import java.util.Random;

import static java.lang.Math.random;

public class NormalNoiseHardMutator implements Mutator<InstanceDouble> {

    private final double mean;
    private final double deviation;
    private final double mutationChance;
    private final Random random;

    public NormalNoiseHardMutator(double deviation, double mutationChance) {
        this(mutationChance, deviation, 0);
    }

    public NormalNoiseHardMutator(double mutationChance, double deviation, double mean) {
        this.random = new Random();
        this.mean = mean;
        this.deviation = deviation;
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(InstanceDouble instance) {
        if (random() < mutationChance) {
            double addon = mean + random.nextGaussian() * deviation;
            for (int i = 0; i < instance.getChromosomes().length; i++)
                instance.getChromosomes()[i] += addon;
        }
    }
}
