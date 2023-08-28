package civitas.celestis.util.function;

/**
 * A function which takes two {@code float}s as input, and returns a {@code float}.
 */
@FunctionalInterface
public interface FloatBinaryOperator {
    /**
     * Applies this function.
     *
     * @param a The first input parameter
     * @param b The second input parameter
     * @return The return value of this function
     */
    float applyAsFloat(float a, float b);
}
