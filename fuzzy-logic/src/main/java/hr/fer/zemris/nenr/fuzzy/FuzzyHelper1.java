package hr.fer.zemris.nenr.fuzzy;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.COADefuzzyfier;
import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.AbstractFuzzySystem;
import hr.fer.zemris.nenr.fuzzy.system.AkcelFuzzySystemMin;
import hr.fer.zemris.nenr.fuzzy.system.KormiloFuzzySystemMin;

import java.util.Scanner;

public class FuzzyHelper1 {

    private static final Defuzzifier defuzzifier = new COADefuzzyfier();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Pick system [A/K]:");
        String systemCode = sc.nextLine();

        AbstractFuzzySystem afs;
        if (systemCode.trim().toLowerCase().equals("a"))
            afs = new AkcelFuzzySystemMin(defuzzifier);
        else
            afs = new KormiloFuzzySystemMin(defuzzifier);
        System.out.println("Rule order [int<" + afs.getRules().size() + "]:");
        int index = Integer.parseInt(sc.nextLine());
        System.out.println("Input params [6 values separated by spaces]:");
        String line = sc.nextLine();
        var input = BoatController.parseInputIntoMap(line);

        System.out.println("Conclusion:");
        System.out.println(afs.getRules().get(index).conclude(input));
    }
}
