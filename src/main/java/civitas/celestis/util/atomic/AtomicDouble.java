package civitas.celestis.util.atomic;

import civitas.celestis.math.Scalars;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic reference to a {@link Double} object. (the boxed form)
 *
 * @see AtomicReference
 */
public class AtomicDouble extends AtomicReference<Double> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a double.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicDouble(double initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code 0}.
     */
    public AtomicDouble() {
        super(0d);
    }

    //
    // Arithmetic
    //

    /**
     * Adds another number to this number.
     *
     * @param n The number of which to add to this number
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double add(double n) {
        return updateAndGet(v -> v + n);
    }

    /**
     * Subtracts this number by another number.
     *
     * @param n The number of which to subtract this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double subtract(double n) {
        return updateAndGet(v -> v - n);
    }

    /**
     * Multiplies this number by another number.
     *
     * @param n The number of which to multiply this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double multiply(double n) {
        return updateAndGet(v -> v * n);
    }

    /**
     * Divide this number by another number. This operation does not allow division by zero.
     * In order to allow division by zero, multiply this number by its multiplicative inverse ({@code 1 / n})
     *
     * @param n The number of which to divide this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the denominator {@code n} is zero
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double divide(double n) throws ArithmeticException {
        if (n == 0) throw new ArithmeticException("Cannot divide by zero.");
        return updateAndGet(v -> v / n);
    }

    /**
     * Raises this number to the {@code e}th power of itself.
     *
     * @param e The exponent of which to raise this number to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double pow(double e) {
        return updateAndGet(v -> Math.pow(v, e));
    }

    /**
     * Sets the value of this reference to the square root of itself.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double sqrt() {
        return updateAndGet(Math::sqrt);
    }

    /**
     * Sets the value of this reference to the factorial of itself.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double factorial() {
        return updateAndGet(Scalars::factorial);
    }

    //
    // Sign
    //

    /**
     * Negates this number, inverting its sign.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double negate() {
        return updateAndGet(v -> -v);
    }

    /**
     * Sets the value of this reference to the absolute of the current value.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When the value of this reference is {@code null}
     */
    public double abs() {
        return updateAndGet(Math::abs);
    }

    //
    // Clamping
    //

    /**
     * Sets the value of this reference to the minimum value between the current value
     * and the provided boundary value {@code n}.
     *
     * @param n The boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public double min(double n) {
        return updateAndGet(v -> Math.min(v, n));
    }

    /**
     * Sets the value of this reference to the maximum value between the current value
     * and the provided boundary value {@code n}.
     *
     * @param n The boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public double max(double n) {
        return updateAndGet(v -> Math.max(v, n));
    }

    /**
     * Sets the value of this reference to the clamped value between the range of {@code [min, max]}.
     *
     * @param min The minimum boundary value to compare to
     * @param max The maximum boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     * @see Scalars#clamp(double, double, double)
     */
    public double clamp(double min, double max) {
        return updateAndGet(v -> Scalars.clamp(v, min, max));
    }

    //
    // Linear Interpolation
    //

    /**
     * Performs linear interpolation (LERP) between this reference's value and the provided
     * ending value {@code e}, using the interpolation parameter {@code t}.
     *
     * @param e The ending value of which to LERP towards
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     * @see Scalars#lerp(double, double, double)
     */
    public double lerp(double e, double t) {
        return updateAndGet(v -> Scalars.lerp(v, e, t));
    }
}
