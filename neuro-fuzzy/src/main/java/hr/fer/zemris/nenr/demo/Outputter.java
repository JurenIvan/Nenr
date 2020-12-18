package hr.fer.zemris.nenr.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Outputter {

    public static void write3DToFile(String filename, List<double[]> in) {
        List<String> lines = new ArrayList<>();
        for (var elem : in) {
            StringBuilder sb = new StringBuilder();
            for (double v : elem) {
                sb.append(v).append(" ");
            }
            lines.add(sb.toString());
        }

        System.out.println(lines);
        try {
            Files.write(Path.of("neuro-fuzzy/src/main/resources/" + filename), lines, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.err.println("failed to write in file!");
        }
    }
}
