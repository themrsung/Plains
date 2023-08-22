package civitas.celestis.util.atomic;

import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vector3;
import jakarta.annotation.Nullable;

/**
 * An atomic reference to a {@link Vector3}.
 *
 * @see AtomicVector
 * @see Vector3
 */
public class AtomicVector3 extends AtomicVector<Vector3> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic reference to a vector.
     *
     * @param initialValue The initial value of this reference
     */
    public AtomicVector3(@Nullable Vector3 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic reference to the value {@code null}
     */
    public AtomicVector3() {
    }

    //
    // Arithmetic
    //

    /**
     * Sets the value of this reference to the cross product between this vector and
     * the provided vector {@code v}.
     *
     * @param v The vector of which to get the cross product between
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided vector {@code v} is {@code null}
     */
    public Vector3 cross(Vector3 v) {
        return updateAndGet(old -> old.cross(v));
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector by the provided rotation quaternion {@code q}.
     *
     * @param q The rotation quaternion of which to rotate by
     * @return The resulting value the reference is pointing to after the operation
     * @throws NullPointerException When either this reference is pointing to {@code null},
     *                              of the provided quaternion {@code q} is {@code null}
     */
    public Vector3 rotate(Quaternion q) {
        return updateAndGet(old -> old.rotate(q));
    }
}
