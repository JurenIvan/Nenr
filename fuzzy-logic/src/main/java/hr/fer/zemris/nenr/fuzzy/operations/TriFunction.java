package hr.fer.zemris.nenr.fuzzy.operations;

import java.util.Objects;
import java.util.function.Function;

/**
 * Natural extension of standard java interfaces Function and Bifunction.
 * Has apply function whose arguments are generified.

 *
 * @param <A> type of first argument in apply method
 * @param <B> type of second argument in apply method
 * @param <C> type of third argument in apply method
 * @param <R> type of result of apply method
 */
@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}