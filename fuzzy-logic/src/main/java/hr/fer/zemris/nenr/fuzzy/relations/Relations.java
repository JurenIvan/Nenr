package hr.fer.zemris.nenr.fuzzy.relations;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

public class Relations {

    private Relations() {
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        return true;
    }

//    public static boolean compositionOfBinaryRelations(IFuzzySet relation1, IFuzzySet relation2) {
//        return true;
//    }
//
//    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
//        return true;
//    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        return true;
    }
}
