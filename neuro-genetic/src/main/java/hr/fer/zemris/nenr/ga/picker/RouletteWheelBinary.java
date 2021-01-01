package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.InstanceBinary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RouletteWheelBinary implements Picker<InstanceBinary> {

    private List<Pair<InstanceBinary, Integer>> population;
    private double sum;

    @Override
    public void configure(List<InstanceBinary> population) {
        double populationMax = population.stream().map(InstanceBinary::getFitness).max(Double::compare).orElse(0.0);
        this.population = new ArrayList<>(population.size());

        int counter = 0;
        for (InstanceBinary instance : population) {
            var newInstance = new InstanceBinary(instance.getChromosomes());
            newInstance.setFitness(Math.log(populationMax + 1) - Math.log(instance.getFitness() + 1));
            this.population.add(new Pair<>(newInstance, counter++));
        }
        sum = this.population.stream().mapToDouble(e -> e.getFirst().getFitness()).sum();
    }

    public int pickOne() {
        double rndNum = Math.random() * sum;
        Iterator<Pair<InstanceBinary, Integer>> iterator = this.population.iterator();
        Pair<InstanceBinary, Integer> result = iterator.next();
        rndNum -= result.getFirst().getFitness();
        while (rndNum > 0) {
            result = iterator.next();
            rndNum -= result.getFirst().getFitness();
        }
        return result.getSecond();
    }

    public static class Pair<T, U> {

        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }
    }
}
