package civitas.celestis.util.function;

/**
 * A function which takes an arbitrary type {@code T} as its input, and returns a {@code float}.
 *
 * @param <T> The input parameter of this function
 */
@FunctionalInterface
public interface ToFloatFunction<T> {
    /**
     * Applies this function.
     *
     * @param t The input parameter
     * @return The return value of this function
     */
    float applyAsFloat(T t);
}
