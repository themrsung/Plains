package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float2;
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
}
