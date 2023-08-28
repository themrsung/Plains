package civitas.celestis.util.function;

/**
 * A unary operator which takes one {@code float} and returns another {@code float}.
 */
@FunctionalInterface
public interface FloatUnaryOperator {
    /**
     * Applies this operator function.
     *
     * @param operand The operand (input value) to supply
     * @return The return value of this function
     */
    float applyAsFloat(float operand);
}
