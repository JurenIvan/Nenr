package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.rule.IRule;

import java.util.List;
import java.util.Map;

public interface FuzzySystem {

    double conclude(Map<String, Integer> input);

    List<IRule> addRules();
}
