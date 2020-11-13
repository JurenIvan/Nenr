package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.domain.Instance;
import hr.fer.zemris.nenr.ga.mutator.Mutator;

import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Leaves eliminationFactor percentage(n) of Instances and rest replaces with best (n)
 */
public class GenerationalSelection implements Selection<Instance> {

    private final double eliminationFactor;
    private final Mutator<Instance> mutator;

    public GenerationalSelection(Mutator<Instance> mutator, double eliminationFactor) {
        this.eliminationFactor = eliminationFactor;
        this.mutator = mutator;
    }

    @Override
    public void doSelection(List<Instance> population) {
        population.sort(comparing(Instance::getFitness));
        int index = 0;
        for (int i = (int) Math.floor(population.size() * eliminationFactor); i < population.size(); i++) {
            var instance = new Instance(population.get(index++).getChromosomes().clone());
            mutator.mutate(instance);
            population.set(i, instance);
        }
    }
}
