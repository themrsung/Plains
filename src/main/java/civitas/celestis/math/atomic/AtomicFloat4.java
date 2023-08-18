package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float4;
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
}
