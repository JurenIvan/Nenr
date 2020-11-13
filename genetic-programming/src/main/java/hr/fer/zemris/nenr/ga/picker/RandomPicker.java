package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.Instance;

import java.util.List;

public class RandomPicker implements Picker<Instance> {

    private int populationSize;

    @Override
    public void configure(List<Instance> population) {
        this.populationSize = population.size();
    }

    public int pickOne() {
        return (int) (Math.random() * populationSize);
    }
}
