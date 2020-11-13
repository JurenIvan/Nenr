package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.Instance;
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
public class TournamentCannonSelection implements Selection<Instance> {

    private final Breeder<Instance> breeder;
    private final Evaluator<Instance> evaluator;
    private final Mutator<Instance> mutator;
    private final Picker<Instance> picker;
    private final int numberOfTournaments;

    public TournamentCannonSelection(Breeder<Instance> breeder, Evaluator<Instance> evaluator, Mutator<Instance> mutator, Picker<Instance> picker, int numberOfTournaments) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.picker = picker;
        this.numberOfTournaments = numberOfTournaments;
    }

    @Override
    public void doSelection(List<Instance> population) {

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

    private boolean breedAndReplaceIfBetter(List<Instance> population, int a, int b, int c) {
        if (population.get(a).getFitness() > max(population.get(b).getFitness(), population.get(c).getFitness())) {  //i3 is worst
            var child = breeder.mate(population.get(b), population.get(c));
            mutator.mutate(child);
            evaluator.evaluate(child);
            if (child.getFitness() < population.get(a).getFitness()) {
                population.set(a, child);
                return true;
            }
        }
        return false;
    }
}
