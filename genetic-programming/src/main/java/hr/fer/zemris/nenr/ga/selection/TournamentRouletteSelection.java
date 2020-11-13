package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.Instance;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;
import hr.fer.zemris.nenr.ga.picker.RouletteWheel;

import java.util.List;

import static java.lang.Math.max;

/**
 * Select numberOfTournaments of pairs of parents. For each pair replace worse parent with child if child is better than worse parent.
 */
public class TournamentRouletteSelection implements Selection<Instance> {

    private final Breeder<Instance> breeder;
    private final Evaluator<Instance> evaluator;
    private final Mutator<Instance> mutator;
    private final int numberOfTournaments;

    public TournamentRouletteSelection(Breeder<Instance> breeder, Evaluator<Instance> evaluator, Mutator<Instance> mutator, int numberOfTournaments) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.numberOfTournaments = numberOfTournaments;
    }

    @Override
    public void doSelection(List<Instance> population) {
        RouletteWheel rouletteWheel = new RouletteWheel();

        for (int i = 0; i < numberOfTournaments; i++) {
            int first = rouletteWheel.pickOne();
            int second = rouletteWheel.pickOne();

            Instance dad = population.get(first);
            Instance mom = population.get(second);

            Instance child = breeder.mate(dad, mom);
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
