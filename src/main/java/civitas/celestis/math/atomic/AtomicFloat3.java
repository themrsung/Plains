package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float3;
import civitas.celestis.math.vector.Quaternion;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Float3}.
 *
 * @see AtomicVector
 */
public class AtomicFloat3 extends AtomicFloatVector<Float3> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicFloat3(@Nullable Float3 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicFloat3() {
    }

    //
    // Arithmetic
    //

    /**
     * Assigns the value of this vector to the cross product between
     * the current value and the provided vector.
     *
     * @param other The vector of which to get the cross product between
     */
    public void cross(@Nonnull Float3 other) {
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
     *
     * @param rq The rotation quaternion to apply to this vector
     */
    public void rotate(@Nonnull Quaternion rq) {
        try {
            getAndUpdate(v -> v.rotate(rq));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
