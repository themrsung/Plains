package civitas.celestis.util.function;

/**
 * A function which takes four parameters of different types as its input,
 * then returns a single value whose type is not necessarily related to its input parameters.
 *
 * @param <T> The first input parameter
 * @param <U> The second input parameter
 * @param <V> The third input parameter
 * @param <W> The fourth input parameter
 * @param <R> The return value of this function
 */
public interface QuadFunction<T, U, V, W, R> {
    /**
     * Applies this function to the provided parameters.
     *
     * @param t The first parameter of this function
     * @param u The second parameter of this function
     * @param v The third parameter of this function
     * @param w The fourth parameter of this function
     * @return The return value of this function
     */
    R apply(T t, U u, V v, W w);
}
