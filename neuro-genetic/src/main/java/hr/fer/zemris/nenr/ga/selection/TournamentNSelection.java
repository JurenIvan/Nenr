package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;
import hr.fer.zemris.nenr.ga.picker.Picker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;

/**
 * Select numberOfTournaments of tuple(3) of instances.
 * For each tuple pick 2 best, and replace third with child of the 2 best if child is better than worst.
 * Suggested to use RandomPicker because configure is called numberOfTournaments times.
 */
public class TournamentNSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Breeder<T> breeder;
    private final Evaluator<T> evaluator;
    private final Mutator<T> mutator;
    private final Picker<T> picker;
    private final int numberOfTournaments;
    private final int tournamentSize;

    public TournamentNSelection(Breeder<T> breeder, Evaluator<T> evaluator, Mutator<T> mutator, Picker<T> picker, int numberOfTournaments, int tournamentSize) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.picker = picker;
        this.numberOfTournaments = numberOfTournaments;
        this.tournamentSize = tournamentSize;
    }

    @Override
    public void doSelection(List<T> population) {
        for (int i = 0; i < numberOfTournaments; i++) {
            picker.configure(population);

            List<Integer> picked = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                picked.add(picker.pickOne());
            }

            var sorted = picked.stream().sorted(comparingDouble(a -> population.get(a).getFitness())).collect(Collectors.toList());

            breedAndReplaceIfBetter(population, sorted.get(sorted.size() - 1), sorted.get(1), sorted.get(2));
        }
    }

    private boolean breedAndReplaceIfBetter(List<T> population, int a, int b, int c) {
        var child = breeder.mate(population.get(b), population.get(c));
        mutator.mutate(child);
        child.setFitness(evaluator.evaluate(child));
        if (child.getFitness() < population.get(a).getFitness()) {
            population.set(a, child);
            return true;
        }
        return false;
    }
}
