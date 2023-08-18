package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Vector;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic reference to a vector. Atomic vectors are null-safe.
 * Any {@link NullPointerException}s will not halt the thread it is being processed in,
 * but instead simply print the stack trace using {@link Throwable#printStackTrace()}.
 *
 * @param <V> The vector of which to reference to
 */
public class AtomicVector<V extends Vector<?, V>> extends AtomicReference<V> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicVector(@Nullable V initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicVector() {
        super(null);
    }

    //
    // Arithmetic
    //

    /**
     * Adds another vector to this vector.
     *
     * @param other The vector to add to this vector
     * @see V#add(Vector)
     */
    public void add(@Nonnull V other) {
        try {
            getAndUpdate(v -> v.add(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other The vector to subtract from this vector
     * @see V#subtract(Vector)
     */
    public void subtract(@Nonnull V other) {
        try {
            getAndUpdate(v -> v.subtract(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Negation
    //

    /**
     * Negates this vector.
     */
    public void negate() {
        try {
            getAndUpdate(V::negate);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Normalization
    //

    /**
     * Normalizes this vector to have a magnitude of {@code 1}.
     *
     * @throws ArithmeticException When the magnitude of this vector is zero
     */
    public void normalize() throws ArithmeticException {
        try {
            getAndUpdate(V::normalize);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Normalizes this vector to have a magnitude of {@code 1},
     * but will set the value to zero if the magnitude is zero.
     */
    public void normalizeOrZero() {
        try {
            getAndUpdate(V::normalizeOrZero);
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Clamping
    //

    /**
     * Sets the value of this vector to the minimum vector between the current
     * value and the provided boundary vector.
     *
     * @param other The boundary vector to compare to
     * @see V#min(Vector)
     */
    public void min(@Nonnull V other) {
        try {
            getAndUpdate(v -> v.min(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the value of this vector to the maximum vector between the current
     * value and the provided boundary vector.
     *
     * @param other The boundary vector to compare to
     * @see V#max(Vector)
     */
    public void max(@Nonnull V other) {
        try {
            getAndUpdate(v -> v.max(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clamps this vector to respect the range of {@code [min, max]}.
     *
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @see V#clamp(Vector, Vector)
     */
    public void clamp(@Nonnull V min, @Nonnull V max) {
        try {
            getAndUpdate(v -> v.clamp(min, max));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
