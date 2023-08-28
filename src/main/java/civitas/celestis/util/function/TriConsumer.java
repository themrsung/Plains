package civitas.celestis.util.function;

/**
 * A function which takes three parameters of different types, and returns no value.
 *
 * @param <T> The first input parameter
 * @param <U> The second input parameter
 * @param <V> The third input parameter
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
    /**
     * Accepts this function, executing its contents.
     *
     * @param t The first input parameter
     * @param u The second input parameter
     * @param v The third input parameter
     */
    void accept(T t, U u, V v);
}
