package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.domain.GASolution;
import hr.fer.zemris.nenr.ga.picker.Picker;

import java.util.ArrayList;
import java.util.List;

/**
 * Replaces population with ones given by picker. Suggested to use Roulette wheel.
 */
public class GenerationalCanonicalSelection<T extends GASolution<P>, P> implements Selection<T> {

    private final Picker<T> instancePicker;


    public GenerationalCanonicalSelection(Picker<T> instancePicker) {
        this.instancePicker = instancePicker;
    }

    @Override
    public void doSelection(List<T> population) {
        instancePicker.configure(population);
        List<T> instances = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            instances.add(population.get(instancePicker.pickOne()));
        }
        population.clear();
        population.addAll(instances);
    }
}
