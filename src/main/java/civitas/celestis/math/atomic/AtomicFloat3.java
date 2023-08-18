package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Float3;
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
}
