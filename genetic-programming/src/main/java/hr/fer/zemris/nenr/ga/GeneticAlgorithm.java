package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.ga.mutator.Mutator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class GeneticAlgorithm {

    private final Mutator<Instance> mutator;
    private final Evaluator<Instance> evaluator;
    private final PopulationInitializer populationInitializer;
    private final Breeder<Instance> breeder;
    private final int elitismCount;
    private final int maxIterationCount;

    private final List<Instance> population = new ArrayList<>();

    public GeneticAlgorithm(Mutator<Instance> mutator, Evaluator<Instance> evaluator, PopulationInitializer populationInitializer, Breeder<Instance> breeder, int elitismCount, int maxIterationCount) {
        this.mutator = mutator;
        this.evaluator = evaluator;
        this.populationInitializer = populationInitializer;
        this.breeder = breeder;
        this.elitismCount = elitismCount;
        this.maxIterationCount = maxIterationCount;
    }

    public void train() {
        initializePopulation();
        evaluatePopulation();
        for (int i = 0; i < maxIterationCount; i++) {
            doSelection();
            doMutation();
            evaluatePopulation();
        }
    }

    public void initializePopulation() {
        population.addAll(populationInitializer.initialize());
    }


    public void evaluatePopulation() {
        population.forEach(e -> e.setFitness(evaluator.evaluate(e)));
    }

    public void doSelection() {
        sortPopulation();
        System.out.println(population.get(0) + " POPFIT:" + populationFitness());
        int replaced = 1;
        for (int i = 0; i < population.size() / 3; i++) {
            Instance dad = population.get(2 * i);
            Instance mom = population.get(2 * i + 1);

            Instance child = breeder.mate(dad, mom);
            child.setFitness(evaluator.evaluate(child));
            if (child.getFitness() < Math.max(dad.getFitness(), mom.getFitness()))
                population.set(population.size() - replaced++, child);
        }
    }

    public void doMutation() {
        population.forEach(mutator::mutate);
    }

    public Instance getFittest() {
        sortPopulation();
        return population.get(0);
    }

    private void sortPopulation() {
        population.sort(comparing(Instance::getFitness));
    }

    public double populationFitness() {
        return population.stream().mapToDouble(Instance::getFitness).sum() / population.size();
    }
}
