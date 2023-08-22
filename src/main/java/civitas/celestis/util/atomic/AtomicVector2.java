package civitas.celestis.util.atomic;

import civitas.celestis.math.vector.Vector2;
import jakarta.annotation.Nullable;

/**
 * An atomic reference to a {@link Vector2}.
 *
 * @see AtomicVector
 * @see Vector2
 */
public class AtomicVector2 extends AtomicVector<Vector2> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a vector.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicVector2(@Nullable Vector2 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}
     */
    public AtomicVector2() {
    }

    //
    // Arithmetic
    //

    /**
     * Sets the value of this reference to the complex number product between this vector and
     * the provided vector {@code v}.
     *
     * @param v The vector of which to get the complex number product between
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     */
    public Vector2 multiply(Vector2 v) {
        return updateAndGet(old -> old.multiply(v));
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector counter-clockwise by the provided angle {@code t}.
     *
     * @param t The angle of rotation to apply, denoted in radians
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When this reference is pointing to {@code null}
     */
    public Vector2 rotate(double t) {
        return updateAndGet(old -> old.rotate(t));
    }
}
