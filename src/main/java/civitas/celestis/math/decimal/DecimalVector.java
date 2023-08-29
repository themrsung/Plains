package civitas.celestis.math.decimal;

import civitas.celestis.math.integer.IntegerVector;
import civitas.celestis.math.vector.BigVector;
import civitas.celestis.math.vector.Vector;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

/**
 * A mathematical vector which uses {@link BigDecimal}s.
 *
 * @param <V>  The vector itself (the parameter and result of various operations)
 * @param <PV> The primitive vector of which this vector can be converted to
 * @see BigVector
 * @see Decimal2
 * @see Decimal3
 * @see Decimal4
 */
public interface DecimalVector<V extends DecimalVector<V, PV>, PV extends Vector<PV>>
        extends BigVector<BigDecimal, BigDecimal, V, V, PV> {
    //
    // Conversion
    //

    /**
     * Converts this vector into a big integer vector.
     *
     * @return The big integer representation of this vector
     */
    @Nonnull
    IntegerVector<?, V, ?> bigIntegerValue();
}
