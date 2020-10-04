package hr.fer.zemris;

import hr.fer.zemris.nenr.domain.DomainElement;
import hr.fer.zemris.nenr.domain.IDomain;

public class DebugUtils {

    public static void print(IDomain domain, String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }
        for (DomainElement e : domain) {
            System.out.println("Element domene: " + e);
        }
        System.out.println("Kardinalitet domene je: " + domain.getCardinality());
        System.out.println();
    }
}
