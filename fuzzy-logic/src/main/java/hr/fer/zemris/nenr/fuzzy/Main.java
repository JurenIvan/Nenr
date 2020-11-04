package hr.fer.zemris.nenr.fuzzy;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.COADefuzzyfier;
import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.AkcelFuzzySystemMin;
import hr.fer.zemris.nenr.fuzzy.system.FuzzySystem;
import hr.fer.zemris.nenr.fuzzy.system.KormiloFuzzySystemMin;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {

    private static final String END_CONDITION = "KRAJ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Defuzzifier def = new COADefuzzyfier();

        FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals(END_CONDITION)) break;

            Map<String, Integer> inParsed = parseIntoMap(line);

            double a = fsAkcel.conclude(inParsed);
            double k = fsKormilo.conclude(inParsed);

            System.out.println(Math.round(a) + " " + Math.round(k));
            System.out.flush();
        }
    }

    private static Map<String, Integer> parseIntoMap(String hasNextLine) {
        String[] parsedInput = hasNextLine.split("\\s+");
        HashMap<String, Integer> map = new HashMap<>();

        map.put("L", parseInt(parsedInput[0]));
        map.put("D", parseInt(parsedInput[1]));
        map.put("LK", parseInt(parsedInput[2]));
        map.put("DK", parseInt(parsedInput[3]));
        map.put("V", parseInt(parsedInput[4]));
        map.put("S", parseInt(parsedInput[5]));

        return map;
    }
}
