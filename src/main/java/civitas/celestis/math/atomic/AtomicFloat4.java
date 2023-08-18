package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float4;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Float4}.
 *
 * @see AtomicVector
 */
public class AtomicFloat4 extends AtomicFloatVector<Float4> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicFloat4(@Nullable Float4 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicFloat4() {
    }

    //
    // Arithmetic
    //

    /**
     * Multiplies this vector by another vector.
     * @param other The vector of which to multiply this vector by
     */
    public void multiply(@Nonnull Float4 other) {
        try {
            getAndUpdate(v -> v.multiply(other));
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
