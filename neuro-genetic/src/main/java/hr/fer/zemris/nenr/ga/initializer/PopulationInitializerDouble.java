package hr.fer.zemris.nenr.ga.initializer;

import hr.fer.zemris.nenr.ga.domain.InstanceDouble;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

public class PopulationInitializerDouble implements Initializer<InstanceDouble> {

    private final int populationSize;
    private final double[] minRange;
    private final double[] maxRange;


    public PopulationInitializerDouble(int populationSize, double[] minRange, double[] maxRange) {
        if (minRange.length != maxRange.length) throw new IllegalStateException();
        this.populationSize = populationSize;

        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public List<InstanceDouble> initialize() {
        List<InstanceDouble> instances = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double[] chromosomes = new double[minRange.length];
            range(0, minRange.length).forEach(e -> chromosomes[e] = generateNumber(e));
            instances.add(new InstanceDouble(chromosomes));
        }
        return instances;
    }

    private double generateNumber(int i) {
        double min = minRange[i];
        return Math.random() * (Math.abs(maxRange[i] - min)) + min;
    }
}
