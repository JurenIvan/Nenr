package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.ga.breeder.SidedAverageBreeder;
import hr.fer.zemris.nenr.ga.evaluator.FunctionEvaluatorDouble;
import hr.fer.zemris.nenr.ga.initializer.PopulationInitializerDouble;
import hr.fer.zemris.nenr.ga.mutator.NormalNoiseMutator;
import hr.fer.zemris.nenr.ga.picker.RouletteWheel;
import hr.fer.zemris.nenr.ga.selection.GenerationalBreederSelection;

import java.util.stream.Collectors;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithm.GeneticAlgorithmHistory;
import static hr.fer.zemris.nenr.ga.functions.Functions.F1;

public class Main {

    public static void main(String[] args) {

        var populationInitializer = new PopulationInitializerDouble(1000, new double[]{-150, -150}, new double[]{50, 50});
        var evaluator = new FunctionEvaluatorDouble(F1);

        var breeder = new SidedAverageBreeder(0.8);
        var mutator = new NormalNoiseMutator(0.1, 0.1);

        var picker = new RouletteWheel();   //doest work with random picker
        var selector = new GenerationalBreederSelection<>(mutator, picker, breeder, true);
        var geneticAlgorithm = new GeneticAlgorithm<>(mutator, evaluator, populationInitializer, selector, 20000, true);

        geneticAlgorithm.train();
        System.out.println(geneticAlgorithm.getFittest());
        System.out.println(geneticAlgorithm.getHistory().stream().map(GeneticAlgorithmHistory::toString).collect(Collectors.joining("\n")));
    }
}
