package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.breeder.Breeder;
import hr.fer.zemris.nenr.ga.domain.Instance;
import hr.fer.zemris.nenr.ga.mutator.Mutator;
import hr.fer.zemris.nenr.ga.picker.Picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static java.util.Comparator.comparing;

/**
 * Selects 2n instances using picker and creates n children which replaces current population.
 */
public class GenerationalBreederSelection implements Selection<Instance> {

    private final Mutator<Instance> mutator;
    private final Picker<Instance> picker;
    private final Breeder<Instance> breeder;
    private final boolean elitism;

    public GenerationalBreederSelection(Mutator<Instance> mutator, Picker<Instance> instancePicker, Breeder<Instance> breeder, boolean elitism) {
        this.mutator = mutator;
        this.picker = instancePicker;
        this.breeder = breeder;
        this.elitism = elitism;
    }

    @Override
    public void doSelection(List<Instance> population) {
        picker.configure(population);
        List<Instance> parents = new ArrayList<>(population.size() * 2);
        for (int i = 0; i < population.size() * 2; i++) {
            parents.add(population.get(picker.pickOne()));
        }
        int populationSize = population.size();
        if (elitism) {
            Instance best = min(population, comparing(Instance::getFitness));
            population.clear();
            population.add(best);
            populationSize--;
        } else {
            population.clear();
        }
        for (int i = 0; i < populationSize; i++) {
            var child = breeder.mate(parents.get(2 * i), parents.get(2 * i + 1));
            mutator.mutate(child);
            population.add(child);
        }
    }
}
