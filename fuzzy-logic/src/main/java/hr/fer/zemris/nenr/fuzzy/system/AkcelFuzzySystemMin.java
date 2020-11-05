package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.defuzzifier.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.rule.CompositeRule;
import hr.fer.zemris.nenr.fuzzy.rule.IRule;
import hr.fer.zemris.nenr.fuzzy.rule.SimpleRule;

import java.util.List;

import static hr.fer.zemris.nenr.fuzzy.system.BoatDomains.*;

public class AkcelFuzzySystemMin extends AbstractFuzzySystem {

    public AkcelFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IRule> addRules() {
        var compositeRule = new CompositeRule(
                new SimpleRule("LK", REALLY_CLOSE),
                new SimpleRule("DK", REALLY_CLOSE));
        compositeRule.setConsequence(DECELERATE_EASY);

        return List.of(
                new SimpleRule("V", SPEED_FAST, DECELERATE_EASY)
                , new SimpleRule("V", SPEED_SLOW, ACCELERATE_EASY)

//                , new SimpleRule("DK", REALLY_CLOSE, ACCELERATE_HARD)
//                , new SimpleRule("LK", REALLY_CLOSE, ACCELERATE_HARD)
                ,compositeRule
        );
    }
}
