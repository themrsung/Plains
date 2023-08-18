package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float2;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Float2}.
 *
 * @see AtomicVector
 */
public class AtomicFloat2 extends AtomicFloatVector<Float2> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicFloat2(@Nullable Float2 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicFloat2() {
    }

    //
    // Arithmetic
    //

    /**
     * Multiplies this vector by the provided vector.
     *
     * @param other The vector of which to multiply this vector to
     * @see Float2#multiply(Float2)
     */
    public void multiply(@Nonnull Float2 other) {
        try {
            getAndUpdate(v -> v.multiply(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector counter-clockwise by the provided angle.
     *
     * @param angRads The angle of rotation to apply in radians
     * @see Float2#rotate(double)
     */
    public void rotate(double angRads) {
        try {
            getAndUpdate(v -> v.rotate(angRads));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
