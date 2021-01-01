package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;
import hr.fer.zemris.nenr.ga.picker.RouletteWheel;

import java.util.List;

import static java.lang.Math.max;

/**
 * Select numberOfTournaments of pairs of parents. For each pair replace worse parent with child if child is better than worse parent.
 */
public class TournamentRouletteSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Breeder<T> breeder;
    private final Evaluator<T> evaluator;
    private final Mutator<T> mutator;
    private final int numberOfTournaments;

    public TournamentRouletteSelection(Breeder<T> breeder, Evaluator<T> evaluator, Mutator<T> mutator, int numberOfTournaments) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.numberOfTournaments = numberOfTournaments;
    }

    @Override
    public void doSelection(List<T> population) {
        RouletteWheel rouletteWheel = new RouletteWheel();

        for (int i = 0; i < numberOfTournaments; i++) {
            int first = rouletteWheel.pickOne();
            int second = rouletteWheel.pickOne();

            T dad = population.get(first);
            T mom = population.get(second);

            T child = breeder.mate(dad, mom);
            mutator.mutate(child);
            child.setFitness(evaluator.evaluate(child));

            if (child.getFitness() > max(dad.getFitness(), mom.getFitness())) return;
            if (dad.getFitness() > max(child.getFitness(), mom.getFitness())) {
                population.set(first, child);
                continue;
            }
            if (mom.getFitness() > max(dad.getFitness(), child.getFitness())) {
                population.set(second, child);
            }
        }
    }
}
