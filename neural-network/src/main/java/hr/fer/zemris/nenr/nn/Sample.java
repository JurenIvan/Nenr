package hr.fer.zemris.nenr.nn;

public class Sample {

    private final double[] sample;
    private final double[] expected;

    public Sample(double[] sample, double[] expected) {
        this.sample = sample;
        this.expected = expected;
    }

    public double[] getSample() {
        return sample;
    }

    public double[] getExpected() {
        return expected;
    }
}
