package civitas.celestis.math.vector;

import java.math.BigDecimal;

/**
 * A mathematical vector which uses {@link BigDecimal} to represents its components.
 * All fixed-size decimal vectors use the ABC notation
 * as opposed to the XYZ notation of {@code double} vectors.
 *
 * @param <V> The vector itself (the result and parameter of various operations)
 * @see BigVector
 */
public interface DecimalVector<V extends DecimalVector<V>> extends BigVector<BigDecimal, V> {
}
