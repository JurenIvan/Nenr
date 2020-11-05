package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.domain.IDomain;
import hr.fer.zemris.nenr.fuzzy.domain.impl.SimpleDomain;
import hr.fer.zemris.nenr.fuzzy.set.CalculatedFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

import static hr.fer.zemris.nenr.fuzzy.unaryfunctions.StandardFuzzySets.*;

public class BoatDomains {

    public static final IDomain POSITION_DOMAIN = new SimpleDomain(0, 1301); //+1 because of SimpleDomainBorders
    public static final IDomain ANGLE_DOMAIN = new SimpleDomain(-90, 91);
    public static final IDomain SPEED_DOMAIN = new SimpleDomain(0, 100);
    public static final IDomain ORIENTATION_DOMAIN = new SimpleDomain(0, 2);
    public static final IDomain ACCELERATION_DOMAIN = new SimpleDomain(-100, 100);

    //speed
    public static final IFuzzySet SPEED_REALLY_SLOW = new CalculatedFuzzySet(SPEED_DOMAIN, lFunction(20, 30));
    public static final IFuzzySet SPEED_SLOW = new CalculatedFuzzySet(SPEED_DOMAIN, lFunction(30, 40));

    public static final IFuzzySet SPEED_FAST = new CalculatedFuzzySet(SPEED_DOMAIN, gammaFunction(45, 60));
    public static final IFuzzySet SPEED_REALLY_FAST = new CalculatedFuzzySet(SPEED_DOMAIN, gammaFunction(60, 70));

    //proximity
    public static final IFuzzySet CLOSE = new CalculatedFuzzySet(POSITION_DOMAIN, lFunction(30, 70));
    public static final IFuzzySet REALLY_CLOSE = new CalculatedFuzzySet(POSITION_DOMAIN, lFunction(30, 50));
    public static final IFuzzySet REALLY_REALLY_CLOSE = new CalculatedFuzzySet(POSITION_DOMAIN, lFunction(15, 20));
    public static final IFuzzySet FAR = new CalculatedFuzzySet(POSITION_DOMAIN, gammaFunction(100, 150));
    public static final IFuzzySet REALLY_FAR = new CalculatedFuzzySet(POSITION_DOMAIN, gammaFunction(150, 200));

    //direction
    public static final IFuzzySet WRONG_DIRECTION = new CalculatedFuzzySet(ORIENTATION_DOMAIN, lFunction(0, 1));

    public static final int ACCELERATION_OFFSET = 100;
    //acceleration
    public static final IFuzzySet ACCELERATE_EASY = new CalculatedFuzzySet(ACCELERATION_DOMAIN, lambdaFunction(ACCELERATION_OFFSET + 5, ACCELERATION_OFFSET + 10, ACCELERATION_OFFSET + 15));
    public static final IFuzzySet DECELERATE_EASY = new CalculatedFuzzySet(ACCELERATION_DOMAIN, lambdaFunction(ACCELERATION_OFFSET - 15, ACCELERATION_OFFSET - 10, ACCELERATION_OFFSET - 5));
    public static final IFuzzySet ACCELERATE_HARD = new CalculatedFuzzySet(ACCELERATION_DOMAIN, lambdaFunction(ACCELERATION_OFFSET + 50, ACCELERATION_OFFSET + 60, ACCELERATION_OFFSET + 70));
    public static final IFuzzySet DECELERATE_HARD = new CalculatedFuzzySet(ACCELERATION_DOMAIN, lambdaFunction(ACCELERATION_OFFSET + -90, ACCELERATION_OFFSET - 70, ACCELERATION_OFFSET + -60));

    public static final int ANGLE_OFFSET = 90;
    //turns
    public static final IFuzzySet TURN_LEFT = new CalculatedFuzzySet(ANGLE_DOMAIN, lambdaFunction(ANGLE_OFFSET + 30, ANGLE_OFFSET + 45, ANGLE_OFFSET + 60));
    public static final IFuzzySet TURN_RIGHT = new CalculatedFuzzySet(ANGLE_DOMAIN, lambdaFunction(ANGLE_OFFSET - 60, ANGLE_OFFSET - 45, ANGLE_OFFSET - 30));
    public static final IFuzzySet TURN_LEFT_HARD = new CalculatedFuzzySet(ANGLE_DOMAIN, gammaFunction(ANGLE_OFFSET + 80, ANGLE_OFFSET + 81));
    public static final IFuzzySet TURN_RIGHT_HARD = new CalculatedFuzzySet(ANGLE_DOMAIN, lFunction(ANGLE_OFFSET - 80, ANGLE_OFFSET - 79));
}
