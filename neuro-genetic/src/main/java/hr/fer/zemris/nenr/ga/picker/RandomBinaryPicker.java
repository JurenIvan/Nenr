package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

import java.util.List;

public class RandomBinaryPicker implements Picker<InstanceBinary> {

    private int populationSize;

    @Override
    public void configure(List<InstanceBinary> population) {
        this.populationSize = population.size();
    }

    public int pickOne() {
        return (int) (Math.random() * populationSize);
    }
}
