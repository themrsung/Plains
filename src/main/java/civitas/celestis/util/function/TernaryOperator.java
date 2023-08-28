package civitas.celestis.util.function;

/**
 * A function which takes three parameters of the same type, then returns a value of that same type.
 *
 * @param <T> The type parameter of this function
 */
@FunctionalInterface
public interface TernaryOperator<T> {
    /**
     * Applies this function, then returns the result of the operation.
     *
     * @param t1 The first input parameter
     * @param t2 The second input parameter
     * @param t3 The third input parameter
     * @return The result of this operation
     */
    T apply(T t1, T t2, T t3);
}
