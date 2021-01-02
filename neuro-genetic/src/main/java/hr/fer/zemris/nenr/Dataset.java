package hr.fer.zemris.nenr;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toList;

public class Dataset {

    private final List<double[]> samples;
    private final int totalArguments;
    private final int result;

    public Dataset(Path filePath, int totalArguments, int result) throws IOException {
        samples = readAllLines(filePath).stream()
                .map(this::parseInput)
                .collect(toList());
        this.totalArguments = totalArguments;
        this.result = result;
    }

    public double[] parseInput(String line) {
        var result = new double[totalArguments];
        var splitted = line.split("\t");
        for (int i = 0; i < totalArguments; i++) {
            result[i] = parseDouble(splitted[i]);
        }
        return result;
    }

    public List<double[]> getSamples() {
        return samples;
    }

    public double[] provideSample(int index) {
        return samples.get(index);
    }

    public int getTotalArguments() {
        return totalArguments;
    }

    public int getResult() {
        return result;
    }

    public int size() {
        return samples.size();
    }
}
