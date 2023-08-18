package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Vector3;
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
}
