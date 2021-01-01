package hr.fer.zemris.nenr.ga.initializer;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class PopulationInitializerBinary implements Initializer<InstanceBinary> {

    private final int populationSize;
    private final int[] numberOfBits;
    private final int dim;

    public PopulationInitializerBinary(int populationSize, int dim, int[] numberOfBits) {
        this.populationSize = populationSize;
        this.numberOfBits = numberOfBits;
        this.dim = dim;
    }

    public PopulationInitializerBinary(int populationSize, int dim, double[] minValue, double[] maxValue, int[] precision) {
        this(populationSize, dim, IntStream.range(0, dim).map(d -> (int) Math.ceil(Math.log((maxValue[d] - minValue[d]) * Math.pow(10, precision[d]) + 1) / Math.log(2))).toArray());
    }

    public List<InstanceBinary> initialize() {
        return range(0, populationSize).mapToObj(e -> initializeInstance()).collect(toList());
    }

    private InstanceBinary initializeInstance() {
        boolean[][] chromosome = new boolean[dim][];
        for (int i = 0; i < dim; i++) {
            boolean[] value = new boolean[numberOfBits[i]];
            chromosome[i] = value;
            for (int j = 0; j < numberOfBits[i]; j++) {
                value[j] = Math.random() > 0.5;
            }
        }
        return new InstanceBinary(chromosome);
    }
}
