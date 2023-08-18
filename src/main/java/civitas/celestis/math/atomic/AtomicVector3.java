package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Quaternion;
import civitas.celestis.math.vector.Vector3;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Vector3}.
 *
 * @see AtomicVector
 */
public class AtomicVector3 extends AtomicDoubleVector<Vector3> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicVector3(@Nullable Vector3 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicVector3() {
    }

    //
    // Arithmetic
    //

    /**
     * Assigns the value of this vector to the cross product between
     * the current value and the provided vector.
     * @param other The vector of which to get the cross product between
     * @see Vector3#cross(Vector3) 
     */
    public void cross(@Nonnull Vector3 other) {
        try {
            getAndUpdate(v -> v.cross(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector by a rotation quaternion.
     * @param rq The rotation quaternion to apply to this vector
     * @see Vector3#rotate(Quaternion) 
     */
    public void rotate(@Nonnull Quaternion rq) {
        try {
            getAndUpdate(v -> v.rotate(rq));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
