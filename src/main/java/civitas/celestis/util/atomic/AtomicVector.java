package civitas.celestis.util.atomic;

import civitas.celestis.math.vector.Vector;
import civitas.celestis.math.vector.Vectors;
import jakarta.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic reference to a vector.
 *
 * @param <V> The vector of which to reference
 * @see AtomicReference
 * @see Vector
 */
public class AtomicVector<V extends Vector<V>> extends AtomicReference<V> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a vector.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicVector(@Nullable V initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}.
     */
    public AtomicVector() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector.
     *
     * @param s The scalar of which to add to this vector
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V add(double s) {
        return updateAndGet(old -> old.add(s));
    }

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s The scalar of which to subtract from this vector
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V subtract(double s) {
        return updateAndGet(old -> old.subtract(s));
    }

    /**
     * Multiplies this vector by scalar.
     *
     * @param s The scalar of which to multiply this vector by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V multiply(double s) {
        return updateAndGet(old -> old.multiply(s));
    }

    /**
     * Divide this vector by a scalar.
     *
     * @param s The scalar of which to divide this vector by
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the denominator {@code s} is zero
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V divide(double s) throws ArithmeticException {
        return updateAndGet(old -> old.divide(s));
    }

    /**
     * Adds another vector to this vector.
     *
     * @param v The vector of which to add to this vector
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     */
    public V add(V v) {
        return updateAndGet(old -> old.add(v));
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v The vector of which to subtract from this vector
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     */
    public V subtract(V v) {
        return updateAndGet(old -> old.subtract(v));
    }

    //
    // Clamping
    //

    /**
     * Sets the value of this reference to the minimum vector between the current value
     * and the provided boundary vector {@code v}.
     *
     * @param v The boundary vector to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     * @see Vector#min(Vector)
     */
    public V min(V v) {
        return updateAndGet(old -> old.min(v));
    }

    /**
     * Sets the value of this reference to the maximum vector between the current value
     * and the provided boundary vector {@code v}.
     *
     * @param v The boundary vector to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     * @see Vector#max(Vector)
     */
    public V max(V v) {
        return updateAndGet(old -> old.max(v));
    }

    /**
     * Sets the value of this reference to the clamped vector between the range of {@code [min, max]}.
     *
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     * @see Vector#clamp(Vector, Vector)
     */
    public V clamp(V min, V max) {
        return updateAndGet(old -> old.clamp(min, max));
    }

    //
    // Negation
    //

    /**
     * Negates this vector.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V negate() {
        return updateAndGet(Vector::negate);
    }

    //
    // Normalization
    //

    /**
     * Normalizes this vector.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the Euclidean norm (the magnitude) of this vector is zero
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V normalize() throws ArithmeticException {
        return updateAndGet(Vector::normalize);
    }

    /**
     * Normalizes this vector, but sets its value to zero if normalization is impossible.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V normalizeOrZero() {
        return updateAndGet(Vector::normalizeOrZero);
    }

    /**
     * Normalizes this vector, but sets its value to the provided fallback value {@code v}
     * if normalization is impossible.
     *
     * @param v The fallback vector to set to if normalization fails
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public V normalizeOrDefault(V v) {
        return updateAndGet(old -> old.normalizeOrDefault(v));
    }

    /**
     * Normalizes this vector, but sets its value to {@code null} if normalization is impossible.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    @SuppressWarnings("ConstantConditions")
    public V normalizeOrNull() {
        return updateAndGet(old -> old.normalizeOrDefault(null));
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * Performs linear interpolation (LERP) between this vector and the provided ending
     * vector {@code e}, using the interpolation parameter {@code t}.
     *
     * @param e The ending vector of which to LERP towards
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided ending vector {@code e} is {@code null}
     */
    public V lerp(V e, double t) {
        return updateAndGet(old -> Vectors.lerp(old, e, t));
    }
}
