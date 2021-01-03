package hr.fer.zemris.nenr.geneticalgorithm.demo;

import hr.fer.zemris.nenr.Dataset;
import hr.fer.zemris.nenr.geneticalgorithm.GeneticAlgorithm;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task5 {

    private static final String outPathRoot = "neuro-genetic/src/main/resources/task5/";
    private static final Path filePath = Paths.get("neuro-genetic/src/main/resources/dataset.txt");
    private static final double MUTATION_DEVIATION = 0.5;

    public static void main(String[] args) throws IOException {
        Dataset dataset = new Dataset(filePath, 5, 3);
        NeuralNetworkEvaluator evaluator = new NeuralNetworkEvaluator(dataset, 8, 4);

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
        var selector = new TournamentNSelection<>(breeder, evaluator, mutator, picker, 20, 20);
        var geneticAlgorithm = new GeneticAlgorithm<>(evaluator, populationInitializer, selector, 1_000_000_000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest().getFitness());
        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithm.GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));

        var best = geneticAlgorithm.getFittest().getChromosomes();

        StringBuilder fileXYStringBuilder = new StringBuilder();
        for (int i = 0; i < 32; i = i + 4) {
            fileXYStringBuilder.append(best[i]).append(" ").append(best[i + 2]).append("\n");
        }

        StringBuilder fileXYWithSStringBuilder = new StringBuilder();
        fileXYWithSStringBuilder.append("x,     abs(s_x),    y,      abs(s_y)\n");
        for (int i = 0; i < 32; i = i + 4) {
            fileXYWithSStringBuilder.append(best[i]).append(" ").append(Math.abs(best[i + 1])).append(" ").append(best[i + 2]).append(" ").append(Math.abs(best[i + 3])).append("\n");
        }

        StringBuilder fileContinuousStringBuilder = new StringBuilder();
        for (var sample : dataset.getSamples()) {
            var result = evaluator.predict(geneticAlgorithm.getFittest(), sample);
            fileContinuousStringBuilder.append(String.format("%s %s %s %s %s%n", sample[0], sample[1], ci(result[0]), ci(result[1]), ci(result[2])));
        }

        StringBuilder fileDiscreteStringBuilder = new StringBuilder();
        for (var sample : dataset.getSamples()) {
            var result = evaluator.predict(geneticAlgorithm.getFittest(), sample);
            if (result[0] > Math.max(result[1], result[2]))
                fileDiscreteStringBuilder.append(String.format("%s %s %s %s %s%n", sample[0], sample[1], 1, 0, 0));
            if (result[1] > Math.max(result[0], result[2]))
                fileDiscreteStringBuilder.append(String.format("%s %s %s %s %s%n", sample[0], sample[1], 0, 1, 0));
            if (result[2] > Math.max(result[0], result[1]))
                fileDiscreteStringBuilder.append(String.format("%s %s %s %s %s%n", sample[0], sample[1], 0, 0, 1));
        }

        Files.writeString(Path.of(outPathRoot + "xy.data"), fileXYStringBuilder.toString());
        Files.writeString(Path.of(outPathRoot + "xy_with_s.data"), fileXYWithSStringBuilder.toString());
        Files.writeString(Path.of(outPathRoot + "continuous.data"), fileContinuousStringBuilder.toString());
        Files.writeString(Path.of(outPathRoot + "discrete.data"), fileDiscreteStringBuilder.toString());
    }

    public static double ci(double x) {
        for (int i = 0; i < 5; i++) {
            if (x < 0.5)
                x = 2 * x * x;
            else
                x = 1 - 2 * (1 - x * x);
        }
        return x;
    }
}
