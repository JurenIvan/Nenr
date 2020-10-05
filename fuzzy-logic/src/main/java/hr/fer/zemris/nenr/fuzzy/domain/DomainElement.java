package hr.fer.zemris.nenr.fuzzy.domain;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;

public class DomainElement {

    private final int[] values;

    private DomainElement(int... values) {
        Objects.requireNonNull(values);

        this.values = values;
    }

    public static DomainElement of(int... values) {
        Objects.requireNonNull(values);

        return new DomainElement(values);
    }

    public int getNumberOfComponents() {
        return values.length;
    }

    public int getComponentValue(int i) {
        return values[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainElement that = (DomainElement) o;

        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return values.length == 1 ? valueOf(values[0]) : Arrays.stream(values).mapToObj(String::valueOf).collect(joining(",", "(", ")"));
    }
}
