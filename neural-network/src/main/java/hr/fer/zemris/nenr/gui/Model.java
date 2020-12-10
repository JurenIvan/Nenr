package hr.fer.zemris.nenr.gui;

import hr.fer.zemris.nenr.PairDouble;
import hr.fer.zemris.nenr.gui.reducer.Reducer;
import hr.fer.zemris.nenr.nn.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;

public class Model {

    private final Map<Character, List<List<PairDouble>>> data;
    private final Reducer<PairDouble> reducer;
    private Character currentKeyState;

    public Model(char defaultChar, Reducer<PairDouble> reducer) {
        this.data = new HashMap<>();
        this.reducer = reducer;
        setKey(defaultChar);
    }

    public void setKey(Character key) {
        this.currentKeyState = key;
        this.data.computeIfAbsent(key, k -> new ArrayList<>());
    }

    public List<List<PairDouble>> getData(Character key) {
        return data.getOrDefault(key, List.of());
    }

    public Map<Character, List<List<PairDouble>>> getData() {
        return data;
    }

    public void addData(List<PairDouble> sample) {
        data.get(currentKeyState).add(sample);
    }

    public void loadDocument(List<String> lines) {
        data.clear();
        for (var line : lines) {
            var splitted = line.split(",");
            List<PairDouble> lineParsed = new ArrayList<>();
            for (int i = 0; i < splitted.length - 1; i += 2) {
                lineParsed.add(new PairDouble(parseDouble(splitted[i]), parseDouble(splitted[i + 1])));
            }
            setKey(line.charAt(line.length() - 1));
            addData(lineParsed);
        }
    }

    public List<String> exportDocument() {
        List<String> result = new ArrayList<>();

        for (var entry : data.entrySet()) {
            for (List<PairDouble> values : entry.getValue()) {
                result.add(values.stream()
                        .map(e -> e.getX() + "," + e.getY())
                        .collect(Collectors.joining(",")) + "," + entry.getKey());
            }
        }
        return result;
    }

    public void clear() {
        this.data.clear();
        setKey(currentKeyState);
    }

    public List<Sample> getSamples() {
        List<Sample> samples = new ArrayList<>();
        int counter = 0;
        final int countOfChars = data.size();
        for (var keyValue : data.entrySet()) {
            for (var sample : keyValue.getValue()) {
                samples.add(new Sample(reducer.reduce(sample), output(counter, countOfChars)));
            }
        }
        return samples;
    }

    public double[] output(int index, int len) {
        double[] result = new double[len];
        result[index] = 1;
        return result;
    }
}
