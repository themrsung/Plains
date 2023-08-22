package civitas.celestis.util.atomic;

import jakarta.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic reference to a {@link Number}. Atomic numbers natively support
 * various arithmetic operations.
 *
 * @param <N> The type of number to reference
 * @see AtomicReference
 */
public abstract class AtomicNumber<N extends Number> extends AtomicReference<N> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a number.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicNumber(@Nullable N initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}.
     */
    public AtomicNumber() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds another number to this number.
     *
     * @param n The number of which to add to this number
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N add(N n);

    /**
     * Subtracts this number by another number.
     *
     * @param n The number of which to subtract this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N subtract(N n);

    /**
     * Multiplies this number by another number.
     *
     * @param n The number of which to multiply this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N multiply(N n);

    /**
     * Divide this number by another number.
     *
     * @param n The number of which to divide this number by
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the denominator {@code n} is zero
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N divide(N n) throws ArithmeticException;

    //
    // Sign
    //

    /**
     * Negates this number, inverting its sign.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public abstract N negate();

    /**
     * Sets the value of this reference to the absolute of the current value.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public abstract N abs();

    //
    // Clamping
    //

    /**
     * Sets the value of this reference to the minimum value between the current value
     * and the provided boundary value {@code n}.
     *
     * @param n The boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N min(N n);

    /**
     * Sets the value of this reference to the maximum value between the current value
     * and the provided boundary value {@code n}.
     *
     * @param n The boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N max(N n);

    /**
     * Sets the value of this reference to the clamped value between the range of {@code [min, max]}.
     *
     * @param min The minimum boundary value to compare to
     * @param max The maximum boundary value to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided number {@code n} is {@code null}
     */
    public abstract N clamp(N min, N max);
}
