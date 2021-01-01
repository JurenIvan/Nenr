package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;
import hr.fer.zemris.nenr.ga.picker.Picker;

import java.util.List;

import static java.lang.Math.max;

/**
 * Select numberOfTournaments of tuple(3) of instances.
 * For each tuple pick 2 best, and replace third with child of the 2 best if child is better than worst.
 * Suggested to use RandomPicker because configure is called numberOfTournaments times.
 */
public class TournamentCannonSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Breeder<T> breeder;
    private final Evaluator<T> evaluator;
    private final Mutator<T> mutator;
    private final Picker<T> picker;
    private final int numberOfTournaments;

    public TournamentCannonSelection(Breeder<T> breeder, Evaluator<T> evaluator, Mutator<T> mutator, Picker<T> picker, int numberOfTournaments) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.picker = picker;
        this.numberOfTournaments = numberOfTournaments;
    }

    @Override
    public void doSelection(List<T> population) {
        for (int i = 0; i < numberOfTournaments; i++) {
            picker.configure(population);

            int i1 = picker.pickOne();
            int i2 = picker.pickOne();
            int i3 = picker.pickOne();

            if (breedAndReplaceIfBetter(population, i1, i2, i3)) continue;
            if (breedAndReplaceIfBetter(population, i2, i3, i1)) continue;
            breedAndReplaceIfBetter(population, i3, i1, i2);
        }
    }

    private boolean breedAndReplaceIfBetter(List<T> population, int a, int b, int c) {
        if (population.get(a).getFitness() > max(population.get(b).getFitness(), population.get(c).getFitness())) {  //i3 is worst
            var child = breeder.mate(population.get(b), population.get(c));
            mutator.mutate(child);
            child.setFitness(evaluator.evaluate(child));
            if (child.getFitness() < population.get(a).getFitness()) {
                population.set(a, child);
                return true;
            }
        }
        return false;
    }
}
