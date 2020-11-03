package hr.fer.zemris.nenr.fuzzy.system;

import java.util.Map;

public interface FuzzySystem {

    double conclude(Map<String, Integer> input);
}
