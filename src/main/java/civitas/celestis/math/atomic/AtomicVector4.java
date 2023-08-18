package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Vector4;
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
}
