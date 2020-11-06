package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.COADefuzzyfier;
import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KormiloFuzzySystemMinTest {

    @Test
    void addRules() {
        Defuzzifier defuzzifier = new COADefuzzyfier();
        AbstractFuzzySystem kormilo = new KormiloFuzzySystemMin(defuzzifier);

        Assertions.assertEquals(6, kormilo.getRules().size());
    }
}
