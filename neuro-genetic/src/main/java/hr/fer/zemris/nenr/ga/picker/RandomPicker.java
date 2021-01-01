package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.InstanceDouble;

import java.util.List;

public class RandomPicker implements Picker<InstanceDouble> {

    private int populationSize;

    @Override
    public void configure(List<InstanceDouble> population) {
        this.populationSize = population.size();
    }

    public int pickOne() {
        return (int) (Math.random() * populationSize);
    }
}
