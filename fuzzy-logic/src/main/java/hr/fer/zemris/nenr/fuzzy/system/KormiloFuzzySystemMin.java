package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.rule.CompositeRule;
import hr.fer.zemris.nenr.fuzzy.rule.IRule;
import hr.fer.zemris.nenr.fuzzy.rule.SimpleRule;

import java.util.List;

public class KormiloFuzzySystemMin extends AbstractFuzzySystem {

    public KormiloFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IRule> addRules() {
        var compositeRule = new CompositeRule(
                new SimpleRule("LK", BoatDomains.REALLY_CLOSE),
                new SimpleRule("DK", BoatDomains.REALLY_CLOSE));
        compositeRule.setConsequence(BoatDomains.TURN_RIGHT_HARD);

        return List.of(
//                new SimpleRule("L", BoatDomains.CLOSE, BoatDomains.TURN_RIGHT),
                new SimpleRule("LK", BoatDomains.CLOSE, BoatDomains.TURN_RIGHT),
//                new SimpleRule("D", BoatDomains.CLOSE, BoatDomains.TURN_LEFT),
                new SimpleRule("DK", BoatDomains.CLOSE, BoatDomains.TURN_LEFT),

//                new SimpleRule("L", BoatDomains.REALLY_CLOSE, BoatDomains.TURN_RIGHT_HARD),
                new SimpleRule("D", BoatDomains.REALLY_CLOSE, BoatDomains.TURN_LEFT_HARD),
//                new SimpleRule("LK", BoatDomains.REALLY_CLOSE, BoatDomains.TURN_RIGHT_HARD),
                new SimpleRule("DK", BoatDomains.REALLY_CLOSE, BoatDomains.TURN_LEFT_HARD)

                , new SimpleRule("S", BoatDomains.WRONG_DIRECTION, BoatDomains.TURN_RIGHT_HARD)

//                new SimpleRule("LK", BoatDomains.REALLY_REALLY_CLOSE, BoatDomains.TURN_RIGHT_HARD),
//                new SimpleRule("DK", BoatDomains.REALLY_REALLY_CLOSE, BoatDomains.TURN_LEFT_HARD)
                , compositeRule
        );
    }
}
