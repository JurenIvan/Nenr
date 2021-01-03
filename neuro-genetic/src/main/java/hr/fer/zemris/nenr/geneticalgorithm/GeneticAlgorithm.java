package hr.fer.zemris.nenr.geneticalgorithm;

import hr.fer.zemris.nenr.geneticalgorithm.domain.GASolution;
import hr.fer.zemris.nenr.geneticalgorithm.evaluator.Evaluator;
import hr.fer.zemris.nenr.geneticalgorithm.initializer.Initializer;
import hr.fer.zemris.nenr.geneticalgorithm.selection.Selection;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.min;
import static java.util.Comparator.comparing;

public class GeneticAlgorithm<T extends GASolution<?>> {

    private final Evaluator<T> evaluator;
    private final Initializer<T> initializer;
    private final Selection<T> selection;
    private final CountingFunction<double[], Double> function;
    private final List<GeneticAlgorithmHistory<T>> history;
    private final int maxIterationCount;

    private final List<T> population = new ArrayList<>();

    public GeneticAlgorithm(Evaluator<T> evaluator, Initializer<T> initializer, Selection<T> selection, int maxIterationCount, boolean keepHistory) {
        this.maxIterationCount = maxIterationCount;
        this.initializer = initializer;
        this.evaluator = evaluator;
        this.selection = selection;
        this.history = keepHistory ? new ArrayList<>() : null;
        this.function = evaluator.getFunction();
    }

    public void train() {
        population.addAll(initializer.initialize());
        population.forEach(e -> e.setFitness(evaluator.evaluateErrorOnDataset(e)));

        for (int i = 0; function.getCounter() < maxIterationCount; i++) {
            selection.doSelection(population);
            population.forEach(e -> e.setFitness(evaluator.evaluateErrorOnDataset(e)));
            conditionallySave(i + 1);
        }
    }

    private void conditionallySave(int generation) {
        if (history == null) return;
        T best = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            if (best.getFitness() > population.get(i).getFitness()) {
                best = population.get(i);
            }
        }
        history.add(new GeneticAlgorithmHistory<>(generation, populationFitness(), best));
    }

    public T getFittest() {
        return min(population, comparing(e -> e.getFitness()));
    }

    public double populationFitness() {
        return population.stream().mapToDouble(T::getFitness).sum() / population.size();
    }

    public List<GeneticAlgorithmHistory<T>> getHistory() {
        return history;
    }

    public static class GeneticAlgorithmHistory<T> {
        private final int generation;
        private final double generationFitness;
        private final T bestInstance;

        public GeneticAlgorithmHistory(int generation, double generationFitness, T bestInstance) {
            this.generation = generation;
            this.generationFitness = generationFitness;
            this.bestInstance = bestInstance;
        }

        public int getGeneration() {
            return generation;
        }

        public double getGenerationFitness() {
            return generationFitness;
        }

        public T getBestInstance() {
            return bestInstance;
        }

        @Override
        public String toString() {
            return "History{gen=%6d, generationFitness=%10.3f, bestInstance=%s}".formatted(generation, generationFitness, bestInstance);
        }
    }
}
