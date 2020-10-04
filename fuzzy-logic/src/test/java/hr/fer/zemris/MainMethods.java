package hr.fer.zemris;

import hr.fer.zemris.nenr.domain.DomainElement;
import hr.fer.zemris.nenr.domain.IDomain;
import hr.fer.zemris.nenr.domain.impl.Domain;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MainMethods {

    /**
     * Expected output:
     * <p>
     * Elementi domene d1:
     * Element domene: 0
     * Element domene: 1
     * Element domene: 2
     * Element domene: 3
     * Element domene: 4
     * Kardinalitet domene je: 5
     * Elementi domene d2:
     * Element domene: 0
     * Element domene: 1
     * Element domene: 2
     * Kardinalitet domene je: 3
     * Elementi domene d3:
     * Element domene: (0,0)
     * Element domene: (0,1)
     * Element domene: (0,2)
     * Element domene: (1,0)
     * Element domene: (1,1)
     * Element domene: (1,2)
     * Element domene: (2,0)
     * Element domene: (2,1)
     * Element domene: (2,2)
     * Element domene: (3,0)
     * Element domene: (3,1)
     * Element domene: (3,2)
     * Element domene: (4,0)
     * Element domene: (4,1)
     * Element domene: (4,2)
     * Kardinalitet domene je: 15
     * (0,0)
     * (1,2)
     * (4,2)
     * 13
     */
    @Test
    @Disabled
    public void zadaca1Zadatak1() {
        IDomain d1 = Domain.intRange(0, 5); // {0,1,2,3,4}
        DebugUtils.print(d1, "Elementi domene d1:");
        IDomain d2 = Domain.intRange(0, 3); // {0,1,2}
        DebugUtils.print(d2, "Elementi domene d2:");
        IDomain d3 = Domain.combine(d1, d2);
        DebugUtils.print(d3, "Elementi domene d3:");
        System.out.println(d3.elementForIndex(0));
        System.out.println(d3.elementForIndex(5));
        System.out.println(d3.elementForIndex(14));
        System.out.println(d3.indexOfElement(DomainElement.of(4, 1)));
    }

}