package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.ga.evaluator.FunctionEvaluator;
import hr.fer.zemris.nenr.ga.evaluator.IFunction;
import hr.fer.zemris.nenr.ga.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMutator;
import hr.fer.zemris.nenr.ga.selection.GenerationalSelection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithm.GeneticAlgorithmHistory;
import static java.lang.Math.*;

public class Main {

    //[0.37144372429906697, -0.12257804101556517, 3.5173807901108254, 1.3175129202376694, -1.3255738972224975]
    //[2.770188872182184, 0.12257770049034844, 3.517405266052586, 1.3175153247431113, -1.325560038261751]
    private static final Path INPUT_FILE_PATH_CLEAN = Path.of("./genetic-programming/src/test/resources/dataset_1-no_noise.txt");

    //[0.37684241071642627, -0.12097669656766702, 3.5126865061175434, 1.3211054529306394, -1.3283749715409514]
    //[2.7647349034784883, 0.12100391534860307, 3.512692952736661, 1.3211215307936355, -1.3283967345070269]
    private static final Path INPUT_FILE_PATH_NOISY = Path.of("./genetic-programming/src/test/resources/dataset_2-noisy.txt");

    private static final IFunction FUNCTION = (x, koef) -> sin(koef[0] + koef[1] * x[0]) + koef[2] * cos(x[0] * (koef[3] + x[1])) / (1 + exp(pow(x[0] - koef[4], 2)));

    public static void main(String[] args) throws IOException {

        var entryProvider = new EntryProvider(INPUT_FILE_PATH_CLEAN);
        var populationInitializer = new PopulationInitializer(100, -4, 4);
        var evaluator = new FunctionEvaluator(entryProvider, FUNCTION);
//        var mutator = new NormalNoiseMutator(0.5, 1);
//        var breeder = new SidedAverageBreeder(0.5);
//        var selector = new TournamentSelection(breeder, evaluator, mutator);
//        var selector = new TournamentModifiedSelection(breeder, evaluator, mutator);
        var mutator = new NormalNoiseMutator(0.5, 0.01);
        var selector = new GenerationalSelection(mutator, 0.3);

        var geneticAlgorithm = new GeneticAlgorithm<>(mutator, evaluator, populationInitializer, selector, 5000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest());
        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));


    }
}
