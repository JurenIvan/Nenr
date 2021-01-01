package hr.fer.zemris.nenr.ga.mutator;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

import java.util.Random;

public class BinaryMutator implements Mutator<InstanceBinary> {

    private final double mutationChance;
    private final Random random;

    public BinaryMutator(double mutationChance) {
        this.random = new Random();
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(InstanceBinary instance) {
        for (int i = 0; i < instance.getChromosomes().length; i++)
            for (int j = 0; j < instance.getChromosomes()[i].length; j++) {
                if (random.nextDouble() < mutationChance)
                    instance.getChromosomes()[i][j] = !instance.getChromosomes()[i][j];
            }
    }
}
