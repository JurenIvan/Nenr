package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.Instance;
import hr.fer.zemris.nenr.ga.evaluator.Evaluator;
import hr.fer.zemris.nenr.ga.mutator.Mutator;

import java.util.List;

import static java.util.Comparator.comparing;

public class TournamentModifiedSelection implements Selection<Instance> {

    private final Breeder<Instance> breeder;
    private final Evaluator<Instance> evaluator;
    private final Mutator<Instance> mutator;

    public TournamentModifiedSelection(Breeder<Instance> breeder, Evaluator<Instance> evaluator, Mutator<Instance> mutator) {
        this.breeder = breeder;
        this.evaluator = evaluator;
        this.mutator = mutator;
    }

    @Override
    public void doSelection(List<Instance> population) {
        population.sort(comparing(Instance::getFitness));
        int replaced = 1;
        for (int i = 0; i < population.size() / 3; i++) {
            Instance dad = population.get(2 * i);
            Instance mom = population.get(2 * i + 1);

            Instance child = breeder.mate(dad, mom);
            mutator.mutate(child);
            child.setFitness(evaluator.evaluate(child));
            if (child.getFitness() < Math.max(dad.getFitness(), mom.getFitness()))
                population.set(population.size() - replaced++, child);
        }
    }
}
