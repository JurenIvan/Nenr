package hr.fer.zemris.nenr.fuzzy.unaryfunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.nenr.fuzzy.unaryfunctions.StandardFuzzySets.*;

class StandardFuzzySetsTest {


    @Test
    public void lambdaFunction_valueAt() {

        var func = lambdaFunction(0, 5, 10);

        Assertions.assertEquals(0, func.valueAt(-1));
        Assertions.assertEquals(0.2, func.valueAt(1));

        Assertions.assertEquals(0.4, func.valueAt(2));
        Assertions.assertEquals(0.6, func.valueAt(7));

        Assertions.assertEquals(0.2, func.valueAt(9));
        Assertions.assertEquals(0, func.valueAt(11));
    }

    @Test
    public void gammaFunction_valueAt() {

        var func = gammaFunction(0, 10);

        Assertions.assertEquals(0, func.valueAt(-1));
        Assertions.assertEquals(0.1, func.valueAt(1));

        Assertions.assertEquals(0.2, func.valueAt(2));
        Assertions.assertEquals(0.7, func.valueAt(7));

        Assertions.assertEquals(0.9, func.valueAt(9));
        Assertions.assertEquals(1, func.valueAt(11));
    }

    @Test
    public void lFunction_valueAt() {

        var func = lFunction(0, 10);

        Assertions.assertEquals(1, func.valueAt(-1));
        Assertions.assertEquals(0.9, func.valueAt(1));

        Assertions.assertEquals(0.8, func.valueAt(2));
        Assertions.assertEquals(0.3, func.valueAt(7));

        Assertions.assertEquals(0.1, func.valueAt(9));
        Assertions.assertEquals(0, func.valueAt(11));
    }
}