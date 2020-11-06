package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.COADefuzzyfier;
import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractFuzzySystemTest {

    //Cannot test conclude without mock (powermock because of static methods)

    @Test
    void getRules() {
        Defuzzifier defuzzifier = new COADefuzzyfier();
        AbstractFuzzySystem afs = new AkcelFuzzySystemMin(defuzzifier);

        Assertions.assertEquals(3, afs.getRules().size());
    }
}
