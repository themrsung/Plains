package civitas.celestis.util.atomic;

import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nullable;

/**
 * An atomic reference to a {@link Vector4}.
 *
 * @see AtomicVector
 * @see Vector4
 */
public class AtomicVector4 extends AtomicVector<Vector4> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a vector.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicVector4(@Nullable Vector4 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}
     */
    public AtomicVector4() {
    }

    //
    // Arithmetic
    //

    /**
     * Sets the value of this reference to the quaternion left-product between this vector and
     * the provided vector {@code v}.
     *
     * @param v The vector of which to get the quaternion left-product between
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     */
    public Vector4 multiply(Vector4 v) {
        return updateAndGet(old -> old.multiply(v));
    }
}
