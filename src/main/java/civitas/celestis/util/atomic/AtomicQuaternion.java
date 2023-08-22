package civitas.celestis.util.atomic;

import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vectors;
import jakarta.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic reference to a quaternion.
 *
 * @see AtomicReference
 * @see Quaternion
 */
public class AtomicQuaternion extends AtomicReference<Quaternion> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a quaternion.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicQuaternion(@Nullable Quaternion initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}.
     */
    public AtomicQuaternion() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this quaternion.
     *
     * @param s The scalar of which to add to this quaternion
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion add(double s) {
        return updateAndGet(old -> old.add(s));
    }

    /**
     * Subtracts a scalar from this quaternion.
     *
     * @param s The scalar of which to subtract from this quaternion
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion subtract(double s) {
        return updateAndGet(old -> old.subtract(s));
    }

    /**
     * Multiplies this quaternion by scalar.
     *
     * @param s The scalar of which to multiply this quaternion by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion multiply(double s) {
        return updateAndGet(old -> old.multiply(s));
    }

    /**
     * Divide this quaternion by a scalar.
     *
     * @param s The scalar of which to divide this quaternion by
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the denominator {@code s} is zero
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion divide(double s) throws ArithmeticException {
        return updateAndGet(old -> old.divide(s));
    }

    /**
     * Adds another quaternion to this quaternion.
     *
     * @param q The quaternion of which to add to this quaternion
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided quaternion {@code q} is {@code null}
     */
    public Quaternion add(Quaternion q) {
        return updateAndGet(old -> old.add(q));
    }

    /**
     * Subtracts another quaternion from this quaternion.
     *
     * @param q The quaternion of which to subtract from this quaternion
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided quaternion {@code q} is {@code null}
     */
    public Quaternion subtract(Quaternion q) {
        return updateAndGet(old -> old.subtract(q));
    }

    //
    // Negation
    //

    /**
     * Negates this quaternion.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion negate() {
        return updateAndGet(Quaternion::negate);
    }

    //
    // Normalization
    //

    /**
     * Normalizes this quaternion.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws ArithmeticException  When the Euclidean norm (the magnitude) of this quaternion is zero
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion normalize() throws ArithmeticException {
        return updateAndGet(Quaternion::normalize);
    }

    /**
     * Normalizes this quaternion, but sets its value to zero if normalization is impossible.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion normalizeOrZero() {
        return updateAndGet(Quaternion::normalizeOrZero);
    }

    /**
     * Normalizes this quaternion, but sets its value to the provided fallback value {@code v}
     * if normalization is impossible.
     *
     * @param v The fallback quaternion to set to if normalization fails
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Quaternion normalizeOrDefault(Quaternion v) {
        return updateAndGet(old -> old.normalizeOrDefault(v));
    }

    /**
     * Normalizes this quaternion, but sets its value to {@code null} if normalization is impossible.
     *
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    @SuppressWarnings("ConstantConditions")
    public Quaternion normalizeOrNull() {
        return updateAndGet(old -> old.normalizeOrDefault(null));
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * Performs linear interpolation (LERP) between this quaternion and the provided ending
     * quaternion {@code e}, using the interpolation parameter {@code t}.
     *
     * @param e The ending quaternion of which to LERP towards
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided ending quaternion {@code e} is {@code null}
     */
    public Quaternion lerp(Quaternion e, double t) {
        return updateAndGet(old -> Vectors.lerp(old, e, t));
    }

    //
    // Spherical Linear Interpolation (SLERP)
    //

    /**
     * Performs spherical linear interpolation (SLERP) between this quaternion and the provided
     * ending quaternion {@code e}, using the interpolation parameter {@code t}.
     *
     * @param e The ending quaternion of which to SLERP towards
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided ending quaternion {@code e} is {@code null}
     */
    public Quaternion slerp(Quaternion e, double t) {
        return updateAndGet(old -> Vectors.slerp(old, e, t));
    }
}
