package civitas.celestis.util.function;

/**
 * A function which takes one {@code float} as its input, and returns one {@code boolean}.
 */
@FunctionalInterface
public interface FloatPredicate {
    /**
     * Tests a value with this predicate.
     *
     * @param value The value of which to test
     * @return The return value of this function
     */
    boolean test(float value);
}
