package civitas.celestis.util.function;

/**
 * A function which takes one {@code float} as its input, and returns
 * an arbitrary type {@code T}.
 *
 * @param <T> The return type of this function
 */
@FunctionalInterface
public interface FloatFunction<T> {
    /**
     * Applies this function.
     *
     * @param value The value of which to apply this function to
     * @return The return value of this function
     */
    T apply(float value);
}
