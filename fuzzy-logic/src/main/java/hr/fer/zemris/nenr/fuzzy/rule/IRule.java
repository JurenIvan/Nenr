package hr.fer.zemris.nenr.fuzzy.rule;

import hr.fer.zemris.nenr.fuzzy.set.IFuzzySet;

import java.util.Map;

public interface IRule {

    IFuzzySet conclude(Map<String, Integer> input);
}
