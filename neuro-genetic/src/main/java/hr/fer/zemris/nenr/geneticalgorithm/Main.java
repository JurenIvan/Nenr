package hr.fer.zemris.nenr.geneticalgorithm;

import hr.fer.zemris.nenr.Dataset;
import hr.fer.zemris.nenr.geneticalgorithm.breeder.Breeder;
import hr.fer.zemris.nenr.geneticalgorithm.breeder.CompositeBreeder;
import hr.fer.zemris.nenr.geneticalgorithm.breeder.CopyChromosome;
import hr.fer.zemris.nenr.geneticalgorithm.breeder.SidedAverageBreeder;
import hr.fer.zemris.nenr.geneticalgorithm.domain.InstanceDouble;
import hr.fer.zemris.nenr.geneticalgorithm.evaluator.NeuralNetworkEvaluator;
import hr.fer.zemris.nenr.geneticalgorithm.initializer.PopulationInitializerDouble;
import hr.fer.zemris.nenr.geneticalgorithm.mutator.CompositeMutator;
import hr.fer.zemris.nenr.geneticalgorithm.mutator.NormalNoiseHardMutator;
import hr.fer.zemris.nenr.geneticalgorithm.mutator.NormalNoiseMediumMutator;
import hr.fer.zemris.nenr.geneticalgorithm.mutator.NormalNoiseMutator;
import hr.fer.zemris.nenr.geneticalgorithm.picker.RouletteWheel;
import hr.fer.zemris.nenr.geneticalgorithm.selection.TournamentNSelection;

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
        NeuralNetworkEvaluator evaluator = new NeuralNetworkEvaluator(dataset, 12);

        double[] lowerBounds = new double[evaluator.getNumberOfParameters()];
        double[] upperBounds = new double[evaluator.getNumberOfParameters()];
        Arrays.fill(lowerBounds, -0.5);
        Arrays.fill(upperBounds, 0.5);

        var populationInitializer = new PopulationInitializerDouble(100, lowerBounds, upperBounds);
        Breeder<InstanceDouble> breeder = new CompositeBreeder<>(List.of(
                new SidedAverageBreeder(0.8),
                new SidedAverageBreeder(0),
                new CopyChromosome(0.75)),
                List.of(2.0, 1.0, 10.0));
        var mutator = new CompositeMutator<>(List.of(
                new NormalNoiseHardMutator(MUTATION_DEVIATION, 0.1),                    // sve za isto
                new NormalNoiseHardMutator(MUTATION_DEVIATION * 2, 0.1),       // sve za isto
                new NormalNoiseMediumMutator(MUTATION_DEVIATION, 0.1),                  // sve ali svaki razlicito
                new NormalNoiseMutator(MUTATION_DEVIATION, 0.1)),                      // za svakog se pitaj i razlicito dodaj
                List.of(1.0, 0.5, 2.0, 8.0));

        var picker = new RouletteWheel();   //doest work with random picker
//        var selector = new GenerationalBreederSelection<>(mutator, picker, breeder, true);
//        var selector = new TournamentCannonSelection<>(breeder, evaluator, mutator, new RouletteWheel(), 20);
        var selector = new TournamentNSelection<>(breeder, evaluator, mutator, picker, 20, 20);
        var geneticAlgorithm = new GeneticAlgorithm<>(evaluator, populationInitializer, selector, 1000000000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest().getFitness());
        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithm.GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));
    }
}
