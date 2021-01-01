package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;

import java.util.List;

import static java.lang.Math.max;
import static java.util.Comparator.comparing;

/**
 * Replaces worst n/3 intances with children of best 2/3
 */
public class TournamentSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Breeder<T> breeder;
    private final Evaluator<T> evaluator;
    private final Mutator<T> mutator;

    public TournamentSelection(Breeder<T> breeder, Evaluator<T> evaluator, Mutator<T> mutator) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
    }

    @Override
    public void doSelection(List<T> population) {
        population.sort(comparing(T::getFitness));
        for (int i = 0; i < population.size() / 2; i++) {
            T dad = population.get(2 * i);
            T mom = population.get(2 * i + 1);

            T child = breeder.mate(dad, mom);
            mutator.mutate(child);
            child.setFitness(evaluator.evaluate(child));
            if (child.getFitness() > max(dad.getFitness(), mom.getFitness())) return;
            if (dad.getFitness() > max(child.getFitness(), mom.getFitness())) population.set(2 * i, child);
            if (mom.getFitness() > max(dad.getFitness(), child.getFitness())) population.set(2 * i + 1, child);
        }
    }
}
