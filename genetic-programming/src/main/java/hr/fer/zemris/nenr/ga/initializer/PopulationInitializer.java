package hr.fer.zemris.nenr.ga.initializer;

import hr.fer.zemris.nenr.ga.domain.Instance;

import java.util.ArrayList;
import java.util.List;

public class PopulationInitializer implements Initializer<Instance> {

    private final int populationSize;
    private final double minRange;
    private final double maxRange;

    public PopulationInitializer(int populationSize, double minRange, double maxRange) {
        this.populationSize = populationSize;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public List<Instance> initialize() {
        List<Instance> instances = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            instances.add(new Instance(generateNumber(), generateNumber(), generateNumber(), generateNumber(), generateNumber()));
        }
        return instances;
    }

    private double generateNumber() {
        return Math.random() * (Math.abs(maxRange - minRange)) + minRange;
    }
}
