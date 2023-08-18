package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Vector3;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Vector4}.
 *
 * @see AtomicVector
 */
public class AtomicVector4 extends AtomicDoubleVector<Vector4> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicVector4(@Nullable Vector4 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicVector4() {
    }

    //
    // Arithmetic
    //

    /**
     * Multiplies this vector by another vector.
     * @param other The vector of which to multiply this vector by
     */
    public void multiply(@Nonnull Vector4 other) {
        try {
            getAndUpdate(v -> v.multiply(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
