package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.Dataset;
import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.breeder.CompositeBreeder;
import hr.fer.zemris.nenr.ga.breeder.CopyChromosome;
import hr.fer.zemris.nenr.ga.breeder.SidedAverageBreeder;
import hr.fer.zemris.nenr.ga.domain.InstanceDouble;
import hr.fer.zemris.nenr.ga.evaluator.NeuralNetworkEvaluator;
import hr.fer.zemris.nenr.ga.initializer.PopulationCloseInitializerDouble;
import hr.fer.zemris.nenr.ga.initializer.PopulationInitializerDouble;
import hr.fer.zemris.nenr.ga.mutator.CompositeMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseHardMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMediumMutator;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMutator;
import hr.fer.zemris.nenr.ga.picker.RouletteWheel;
import hr.fer.zemris.nenr.ga.selection.TournamentNSelection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main2 {

    private static final Path filePath = Paths.get("neuro-genetic/src/main/resources/dataset.txt");
    private static final double MUTATION_DEVIATION = 0.5;

    //History{gen=156249, generationFitness=     0.012, bestInstance=Instance{fitness=2.1490888377585777E-6 ,cromosomes=0.12641,-0.10500,0.20849,-0.32756,0.38452,0.06824,0.26149,0.16418,0.63861,0.13006,0.26466,0.24790,0.39343,-0.10406,0.77153,0.34677,0.78356,-11.28875,0.68675,0.40970,0.36292,0.07224,0.72432,0.13052,0.85903,-0.16513,0.25039,0.36383,0.05357,0.14613,0.27446,-0.08560,0.62962,0.18155,0.74431,0.37767,0.85905,0.12933,0.74219,-0.29945,0.84960,0.19407,0.31440,-0.43533,0.09715,-0.13363,0.73819,-0.23100,16.25625,-31.90558,-40.11153,-9.97386,-0.38900,-5.55528,20.76680,31.98798,45.65695,-40.85238,15.02912,-25.06076,-1.13213,-15.99378,64.84901,-20.04179,-14.82496,5.56660,-31.63851,-9.11632,-28.28545,-19.79065,32.38070,-11.06913,34.41589,2.50944,-11.11004,-33.68734,52.43070,24.90395,-5.29337,34.20874,-9.65479,-8.98052,-17.05580,-31.12065,-0.05634,-9.88751,-1.42055}}

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
        var geneticAlgorithm = new GeneticAlgorithm<>(evaluator, populationInitializer, selector, 500_000_000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest());
        System.out.println(geneticAlgorithm.getHistory().get(geneticAlgorithm.getHistory().size() - 1).getGenerationFitness());


        var newInitializr = new PopulationCloseInitializerDouble(100, geneticAlgorithm.getFittest().getChromosomes(), 0.1);
        evaluator = new NeuralNetworkEvaluator(dataset, 12);
        geneticAlgorithm = new GeneticAlgorithm<>(evaluator, newInitializr, selector, 1_000_000_000, true);
        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest());


        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithm.GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));
    }
}
