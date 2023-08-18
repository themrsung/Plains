package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Quaternion;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic {@link Quaternion}.
 */
public class AtomicQuaternion extends AtomicReference<Quaternion> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic quaternion.
     *
     * @param initialValue The initial value of this quaternion
     */
    public AtomicQuaternion(@Nullable Quaternion initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic quaternion. The initial value is {@code null}.
     */
    public AtomicQuaternion() {
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this quaternion.
     *
     * @param s The scalar to add to this quaternion
     * @see Quaternion#add(double)
     */
    public void add(double s) {
        try {
            getAndUpdate(v -> v.add(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtracts a scalar from this quaternion.
     *
     * @param s The scalar to subtract from this vector
     * @see Quaternion#subtract(double)
     */
    public void subtract(double s) {
        try {
            getAndUpdate(v -> v.subtract(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Multiplies this quaternion by a scalar.
     *
     * @param s The scalar to multiply this vector by
     * @see Quaternion#multiply(double)
     */
    public void multiply(double s) {
        try {
            getAndUpdate(v -> v.multiply(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scales this quaternion by the provided factor {@code s}.
     *
     * @param s The scale factor to apply to this quaternion
     * @see Quaternion#scale(double)
     */
    public void scale(double s) {
        try {
            getAndUpdate(v -> v.scale(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Divides this quaternion by a scalar.
     *
     * @param s The scalar to divide this quaternion by
     * @throws ArithmeticException When the denominator {@code s} is zero
     * @see Quaternion#divide(double)
     */
    public void divide(double s) throws ArithmeticException {
        try {
            getAndUpdate(v -> v.divide(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Divides this quaternion by a scalar, but allows division by zero.
     *
     * @param s The scalar to divide this quaternion by
     * @see Quaternion#divideAllowZero(double)
     */
    public void divideAllowZero(double s) {
        try {
            getAndUpdate(v -> v.divideAllowZero(s));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds another vector to this quaternion.
     *
     * @param other The vector to add to this quaternion
     * @see Quaternion#add(Vector4)
     */
    public void add(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.add(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other The vector to subtract from this quaternion
     * @see Quaternion#subtract(Vector4)
     */
    public void subtract(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.subtract(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Multiplies this quaternion by a four-dimensional vector.
     *
     * @param other The vector of which to multiply this vector by
     */
    public void multiply(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.multiply(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Negation
    //

    /**
     * Negates this quaternion.
     */
    public void negate() {
        try {
            getAndUpdate(Quaternion::negate);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Conjugation
    //

    /**
     * Sets the value of this reference to the conjugate of the current value.
     */
    public void conjugate() {
        try {
            getAndUpdate(Quaternion::conjugate);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Inversion
    //

    /**
     * Sets the value of this reference to the inverse of the current value.
     *
     * @throws ArithmeticException When the Euclidean norm of this quaternion is zero
     */
    public void invert() throws ArithmeticException {
        try {
            getAndUpdate(Quaternion::inverse);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Normalization
    //

    /**
     * Normalizes this quaternion to have a magnitude of {@code 1}.
     *
     * @throws ArithmeticException When the magnitude of this quaternion is zero
     */
    public void normalize() throws ArithmeticException {
        try {
            getAndUpdate(Quaternion::normalize);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Normalizes this quaternion to have a magnitude of {@code 1},
     * but will set the value to zero if the magnitude is zero.
     */
    public void normalizeOrZero() {
        try {
            getAndUpdate(Quaternion::normalizeOrZero);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Clamping
    //

    /**
     * Sets the value of this quaternion to the minimum vector between the current
     * value and the provided boundary vector.
     *
     * @param other The boundary vector to compare to
     * @see Quaternion#min(Vector4)
     */
    public void min(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.min(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the value of this quaternion to the maximum vector between the current
     * value and the provided boundary vector.
     *
     * @param other The boundary vector to compare to
     * @see Quaternion#max(Vector4)
     */
    public void max(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.max(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clamps this quaternion to respect the range of {@code [min, max]}.
     *
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @see Quaternion#clamp(Vector4, Vector4)
     */
    public void clamp(@Nonnull Vector4 min, @Nonnull Vector4 max) {
        try {
            getAndUpdate(v -> v.clamp(min, max));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
