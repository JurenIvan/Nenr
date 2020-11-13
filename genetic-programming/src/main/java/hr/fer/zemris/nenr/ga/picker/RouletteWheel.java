package hr.fer.zemris.nenr.ga.picker;

import hr.fer.zemris.nenr.ga.domain.Instance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RouletteWheel implements Picker<Instance> {

    private List<Pair<Instance, Integer>> population;
    private double sum;

    public RouletteWheel() {
    }

    @Override
    public void configure(List<Instance> population) {
        double populationMax = population.stream().map(Instance::getFitness).max(Double::compare).orElse(0.0);
        this.population = new ArrayList<>(population.size());

        int counter = 0;
        for (Instance instance : population) {
            var newInstance = new Instance(instance.getChromosomes());
            newInstance.setFitness(populationMax - instance.getFitness());
            this.population.add(new Pair<>(newInstance, counter++));
        }
        sum = this.population.stream().mapToDouble(e -> e.getFirst().getFitness()).sum();
    }

    public int pickOne() {
        double rndNum = Math.random() * sum;
        Iterator<Pair<Instance, Integer>> iterator = this.population.iterator();
        Pair<Instance, Integer> result = iterator.next();
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
