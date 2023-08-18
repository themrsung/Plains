package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.FloatVector;
import jakarta.annotation.Nullable;

/**
 * An atomic reference to a {@code float} vector.
 *
 * @param <V> The vector of which to reference to
 * @see AtomicVector
 */
public class AtomicFloatVector<V extends FloatVector<V>> extends AtomicVector<V> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicFloatVector(@Nullable V initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicFloatVector() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector.
     *
     * @param s The scalar to add to this vector
     * @see V#add(float)
     */
    public void add(float s) {
        try {
            getAndUpdate(v -> v.add(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s The scalar to subtract from this vector
     * @see V#subtract(float)
     */
    public void subtract(float s) {
        try {
            getAndUpdate(v -> v.subtract(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param s The scalar to multiply this vector by
     * @see V#multiply(float)
     */
    public void multiply(float s) {
        try {
            getAndUpdate(v -> v.multiply(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Divides this vector by a scalar.
     *
     * @param s The scalar to divide this vector by
     * @throws ArithmeticException When the denominator {@code s} is zero
     * @see V#divide(float)
     */
    public void divide(float s) throws ArithmeticException {
        try {
            getAndUpdate(v -> v.divide(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Divides this vector by a scalar, but allows division by zero.
     *
     * @param s The scalar to divide this vector by
     * @see V#divideAllowZero(float)
     */
    public void divideAllowZero(float s) {
        try {
            getAndUpdate(v -> v.divideAllowZero(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
