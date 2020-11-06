package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.COADefuzzyfier;
import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class AkcelFuzzySystemMinTest {

    @Test
    public void addRules() {
        Defuzzifier defuzzifier = new COADefuzzyfier();
        AkcelFuzzySystemMin akcelFuzzySystemMin = new AkcelFuzzySystemMin(defuzzifier);

        Assertions.assertEquals(3, akcelFuzzySystemMin.getRules().size());
    }

    @Test
    public void conclude() {
        Defuzzifier defuzzifier = new COADefuzzyfier();
        AkcelFuzzySystemMin akcelFuzzySystemMin = new AkcelFuzzySystemMin(defuzzifier);
        Map<String, Integer> input = Map.of("L", 60, "D", 70, "LK", 10, "DK", 10, "V", 45, "S", 1);

        var result = akcelFuzzySystemMin.conclude(input);

        Assertions.assertEquals(-10,result,1e-6);
    }

}
