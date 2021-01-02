package hr.fer.zemris.nenr.geneticalgorithm.breeder;

import java.util.List;

public class CompositeBreeder<T> implements Breeder<T> {

    private final double sumOfProbabilities;
    private final List<Breeder<T>> breederList;
    private final List<Double> probabilities;

    public CompositeBreeder(List<Breeder<T>> breederList, List<Double> probabilities) {
        this.breederList = breederList;
        this.probabilities = probabilities;
        this.sumOfProbabilities = probabilities.stream().mapToDouble(e -> e).sum();
    }


    @Override
    public T mate(T father, T mother) {
        Breeder<T> selectedBreeder = breederList.get(0);
        double random = Math.random() * sumOfProbabilities;

        for (int index = 0; random > 0; index++) {
            random -= probabilities.get(index);
            selectedBreeder = breederList.get(index);
        }

        return selectedBreeder.mate(father, mother);
    }
}
