package hr.fer.zemris.nenr.geneticalgorithm.initializer;

import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.IntStream.range;

public class PopulationCloseInitializerDouble implements Initializer<InstanceDouble> {

    private final static Random random = new Random();
    private final int populationSize;
    private final double[] values;
    private final double deviation;

    public PopulationCloseInitializerDouble(int populationSize, double[] values, double deviation) {
        this.populationSize = populationSize;
        this.deviation = deviation;
        this.values = values;
    }

    public List<InstanceDouble> initialize() {
        List<InstanceDouble> instances = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double[] chromosomes = new double[values.length];
            range(0, values.length).forEach(e -> chromosomes[e] = generateNumber(e));
            instances.add(new InstanceDouble(chromosomes));
        }
        return instances;
    }

    private double generateNumber(int i) {
        return values[i] + random.nextGaussian() * deviation;
    }
}
