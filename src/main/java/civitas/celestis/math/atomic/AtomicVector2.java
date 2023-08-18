package civitas.celestis.math.atomic;

import civitas.celestis.math.vector.Vector2;
import jakarta.annotation.Nullable;

/**
 * An atomic {@link Vector2}.
 *
 * @see AtomicVector
 */
public class AtomicVector2 extends AtomicDoubleVector<Vector2> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic vector.
     *
     * @param initialValue The initial value of this vector
     */
    public AtomicVector2(@Nullable Vector2 initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic vector. The initial value is {@code null}.
     */
    public AtomicVector2() {
    }
}
