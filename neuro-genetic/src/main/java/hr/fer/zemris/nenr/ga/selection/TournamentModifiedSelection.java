package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;

import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Replaces worst ones with children of fist 2/3 only if child is better than one of parents
 */
public class TournamentModifiedSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Breeder<T> breeder;
    private final Evaluator<T> evaluator;
    private final Mutator<T> mutator;

    public TournamentModifiedSelection(Breeder<T> breeder, Evaluator<T> evaluator, Mutator<T> mutator) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
    }

    @Override
    public void doSelection(List<T> population) {
        population.sort(comparing(T::getFitness));
        int replaced = 1;
        for (int i = 0; i < population.size() / 3; i++) {
            T dad = population.get(2 * i);
            T mom = population.get(2 * i + 1);

            T child = breeder.mate(dad, mom);
            mutator.mutate(child);
            child.setFitness(evaluator.evaluate(child));
            if (child.getFitness() < Math.max(dad.getFitness(), mom.getFitness()))
                population.set(population.size() - replaced++, child);
        }
    }
}
