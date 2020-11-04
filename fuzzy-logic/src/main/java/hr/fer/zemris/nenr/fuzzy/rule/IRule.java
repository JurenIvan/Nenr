package hr.fer.zemris.nenr.fuzzy.rule;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

import java.util.Map;

public interface IRule {


    IFuzzySet conclude(Map<String, Integer> input);

//    public static final IDomain POSITION_DOMAIN = new SimpleDomain(0, 1301); //+1 because of SimpleDomainBorders
//    public static final IDomain ANGLE_DOMAIN = new SimpleDomain(-90, 91);
//    public static final IDomain SPEED_DOMAIN = new SimpleDomain(0, 100);
//    public static final IDomain ORIENTATION_DOMAIN = new SimpleDomain(0, 2);
//    public static final IDomain ACCELERATION_DOMAIN = new SimpleDomain(-100, 100);
//
//    CalculatedFuzzySet leftSteer =
//            new CalculatedFuzzySet(
//                    ANGLE_DOMAIN,
//                    StandardFuzzySets.lambdaFunction(25,70,75);

}
