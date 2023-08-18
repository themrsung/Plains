package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.DoubleVector;
import civitas.celestis.math.vector.Vector;
import jakarta.annotation.Nullable;

/**
 * An atomic reference to a {@code double} vector.
 *
 * @param <V> The vector of which to reference to
 * @see AtomicVector
 */
public class AtomicDoubleVector<V extends DoubleVector<V>> extends AtomicVector<V> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicDoubleVector(@Nullable V initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicDoubleVector() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector.
     *
     * @param s The scalar to add to this vector
     * @see V#add(double) 
     */
    public void add(double s) {
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
     * @see V#subtract(double) 
     */
    public void subtract(double s) {
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
     * @see V#multiply(double) 
     */
    public void multiply(double s) {
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
     * @see V#divide(double) 
     */
    public void divide(double s) throws ArithmeticException {
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
     * @see V#divideAllowZero(double) 
     */
    public void divideAllowZero(double s) {
        try {
            getAndUpdate(v -> v.divideAllowZero(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
