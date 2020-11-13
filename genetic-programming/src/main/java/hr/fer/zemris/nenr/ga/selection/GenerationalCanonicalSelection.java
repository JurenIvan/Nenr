package hr.fer.zemris.nenr.ga.selection;

import hr.fer.zemris.nenr.ga.domain.Instance;
import hr.fer.zemris.nenr.ga.picker.Picker;

import java.util.ArrayList;
import java.util.List;

/**
 * Replaces population with ones given by picker. Suggested to use Roulette wheel.
 */
public class GenerationalCanonicalSelection implements Selection<Instance> {

    private final Picker<Instance> instancePicker;


    public GenerationalCanonicalSelection(Picker<Instance> instancePicker) {
        this.instancePicker = instancePicker;
    }

    @Override
    public void doSelection(List<Instance> population) {
        instancePicker.configure(population);
        List<Instance> instances = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            instances.add(population.get(instancePicker.pickOne()));
        }
        population.clear();
        population.addAll(instances);
    }
}
