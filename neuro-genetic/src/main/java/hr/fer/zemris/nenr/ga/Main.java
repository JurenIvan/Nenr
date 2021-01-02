package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.Dataset;
import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.breeder.CompositeBreeder;
import hr.fer.zemris.nenr.ga.breeder.SidedAverageBreeder;
import hr.fer.zemris.nenr.ga.domain.InstanceDouble;
import hr.fer.zemris.nenr.ga.evaluator.NeuralNetworkEvaluator;
import hr.fer.zemris.nenr.ga.initializer.PopulationInitializerDouble;
import hr.fer.zemris.nenr.ga.mutator.CompositeMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseHardMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMediumMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMutator;
import hr.fer.zemris.nenr.ga.picker.RouletteWheel;
import hr.fer.zemris.nenr.ga.selection.GenerationalBreederSelection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Path filePath = Paths.get("neuro-genetic/src/main/resources/dataset.txt");
    private static final double MUTATION_DEVIATION = 0.5;

    public static void main(String[] args) throws IOException {

        Dataset dataset = new Dataset(filePath, 5, 3);
        NeuralNetworkEvaluator evaluator = new NeuralNetworkEvaluator(dataset, 3, 8);

        double[] lowerBounds = new double[evaluator.getNumberOfParameters()];
        double[] upperBounds = new double[evaluator.getNumberOfParameters()];
        Arrays.fill(lowerBounds, 0.0);
        Arrays.fill(upperBounds, 1.0);

        var populationInitializer = new PopulationInitializerDouble(1000, lowerBounds, upperBounds);
        Breeder<InstanceDouble> breeder = new CompositeBreeder<>(List.of(new SidedAverageBreeder(0.8), new SidedAverageBreeder(0), new SidedAverageBreeder(1)), List.of(5.0, 3.0, 2.0));
        var mutator = new CompositeMutator<>(List.of(
                new NormalNoiseHardMutator(MUTATION_DEVIATION, 0.1),                    // sve za isto
                new NormalNoiseHardMutator(MUTATION_DEVIATION * 2, 0.1),       // sve za isto
                new NormalNoiseMediumMutator(MUTATION_DEVIATION, 0.1),                  // sve ali svaki razlicito
                new NormalNoiseMutator(MUTATION_DEVIATION, 0.01)),                      // za svakog se pitaj i razlicito dodaj
                List.of(1.0, 1.0, 3.0, 3.0));

        var picker = new RouletteWheel();   //doest work with random picker
        var selector = new GenerationalBreederSelection<>(mutator, picker, breeder, true);
        var geneticAlgorithm = new GeneticAlgorithm<>(evaluator, populationInitializer, selector, 2000000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest());
        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithm.GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));
    }
}
