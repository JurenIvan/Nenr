//package hr.fer.zemris;
//
//import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
//import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
//import hr.fer.zemris.nenr.fuzzy.domain.impl.Domain;
//import hr.fer.zemris.nenr.fuzzy.operations.Operations;
//import hr.fer.zemris.nenr.fuzzy.set.CalculatedFuzzySet;
//import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;
//import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
//import hr.fer.zemris.nenr.fuzzy.unaryfunctions.StandardFuzzySets;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//class MainMethods {
//
//    /**
//     * Expected output:
//     * <p>
//     * Elementi domene d1:
//     * Element domene: 0
//     * Element domene: 1
//     * Element domene: 2
//     * Element domene: 3
//     * Element domene: 4
//     * Kardinalitet domene je: 5
//     * Elementi domene d2:
//     * Element domene: 0
//     * Element domene: 1
//     * Element domene: 2
//     * Kardinalitet domene je: 3
//     * Elementi domene d3:
//     * Element domene: (0,0)
//     * Element domene: (0,1)
//     * Element domene: (0,2)
//     * Element domene: (1,0)
//     * Element domene: (1,1)
//     * Element domene: (1,2)
//     * Element domene: (2,0)
//     * Element domene: (2,1)
//     * Element domene: (2,2)
//     * Element domene: (3,0)
//     * Element domene: (3,1)
//     * Element domene: (3,2)
//     * Element domene: (4,0)
//     * Element domene: (4,1)
//     * Element domene: (4,2)
//     * Kardinalitet domene je: 15
//     * (0,0)
//     * (1,2)
//     * (4,2)
//     * 13
//     */
//    @Test
//    public void zadaca1Zadatak1() {
//        IDomain d1 = Domain.intRange(0, 5); // {0,1,2,3,4}
//        DebugUtils.print(d1, "Elementi domene d1:");
//        IDomain d2 = Domain.intRange(0, 3); // {0,1,2}
//        DebugUtils.print(d2, "Elementi domene d2:");
//        IDomain d3 = Domain.combine(d1, d2);
//        DebugUtils.print(d3, "Elementi domene d3:");
//        System.out.println(d3.elementForIndex(0));
//        System.out.println(d3.elementForIndex(5));
//        System.out.println(d3.elementForIndex(14));
//        System.out.println(d3.indexOfElement(DomainElement.of(4, 1)));
//    }
//
//    @Test
//    public void zadaca1Zadatak2() {
//        IDomain d = Domain.intRange(0, 11); // {0,1,...,10}
//        IFuzzySet set1 = new MutableFuzzySet(d)
//                .set(DomainElement.of(0), 1.0)
//                .set(DomainElement.of(1), 0.8)
//                .set(DomainElement.of(2), 0.6)
//                .set(DomainElement.of(3), 0.4)
//                .set(DomainElement.of(4), 0.2);
//        DebugUtils.print(set1, "Set1:");
//
//        IDomain d2 = Domain.intRange(-5, 6); // {-5,-4,...,4,5}
//        IFuzzySet set2 = new CalculatedFuzzySet(
//                d2,
//                StandardFuzzySets.lambdaFunction(
//                        d2.indexOfElement(DomainElement.of(-4)),
//                        d2.indexOfElement(DomainElement.of(0)),
//                        d2.indexOfElement(DomainElement.of(4))
//                )
//        );
//        DebugUtils.print(set2, "Set2:");
//    }
//
//    @Test
//    public void zadaca1Zadatak3() {
//        IDomain d = Domain.intRange(0, 11);
//        IFuzzySet set1 = new MutableFuzzySet(d)
//                .set(DomainElement.of(0), 1.0)
//                .set(DomainElement.of(1), 0.8)
//                .set(DomainElement.of(2), 0.6)
//                .set(DomainElement.of(3), 0.4)
//                .set(DomainElement.of(4), 0.2);
//        DebugUtils.print(set1, "Set1:");
//
//        IFuzzySet notSet1 = Operations.unaryOperation(set1, Operations.zadehNot());
//        DebugUtils.print(notSet1, "notSet1:");
//
//        IFuzzySet union = Operations.binaryOperation(set1, notSet1, Operations.zadehOr());
//        DebugUtils.print(union, "Set1 union notSet1:");
//
//        IFuzzySet hinters = Operations.binaryOperation(set1, notSet1, Operations.hamacherTNorm(1.0));
//        DebugUtils.print(hinters, "Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");
//    }
//}