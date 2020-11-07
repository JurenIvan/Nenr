package hr.fer.zemris.nenr.ga;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toList;

public class EntryProvider {

    private final List<double[]> samples;

    public EntryProvider(Path filePath) throws IOException {
        samples = readAllLines(filePath).stream()
                .map(this::parseInput)
                .collect(toList());
    }

    public double[] parseInput(String line) {
        var result = new double[3];
        var splitted = line.split("\t");
        result[0] = parseDouble(splitted[0]);
        result[1] = parseDouble(splitted[1]);
        result[2] = parseDouble(splitted[2]);
        return result;
    }

    public List<double[]> provideSamples() {
        return Collections.unmodifiableList(samples);
    }
}
