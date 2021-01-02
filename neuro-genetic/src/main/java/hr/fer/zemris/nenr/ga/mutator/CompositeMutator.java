package hr.fer.zemris.nenr.ga.mutator;

import java.util.List;

public class CompositeMutator<T> implements Mutator<T> {


    private final double sumOfProbabilities;
    private final List<Mutator<T>> mutators;
    private final List<Double> probabilities;

    public CompositeMutator(List<Mutator<T>> mutators, List<Double> probabilities) {
        this.mutators = mutators;
        this.probabilities = probabilities;
        this.sumOfProbabilities = probabilities.stream().mapToDouble(e -> e).sum();
    }

    @Override
    public void mutate(T instance) {
        Mutator<T> selectedBreeder = mutators.get(0);
        double random = Math.random() * sumOfProbabilities;

        for (int index = 0; random > 0; index++) {
            random -= probabilities.get(index);
            selectedBreeder = mutators.get(index);
        }

        selectedBreeder.mutate(instance);
    }
}
