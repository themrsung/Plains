package civitas.celestis.math.integer;

import civitas.celestis.math.vector.BigVector;
import civitas.celestis.util.tuple.BaseTuple;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A mathematical vector which uses {@link BigInteger}s.
 *
 * @param <V>  The vector itself (the parameter and result of various operations)
 * @param <DV> The decimal vector to use for normalization
 * @param <PV> The primitive vector of which this vector can be converted to
 * @see BigVector
 * @see Integer2
 * @see Integer3
 * @see Integer4
 */
public interface IntegerVector<V extends IntegerVector<V, DV, PV>,
        DV extends BigVector<BigDecimal, BigDecimal, DV, DV, ?>,
        PV extends BaseTuple<? extends Number>>
        extends BigVector<BigInteger, BigDecimal, V, DV, PV> {
}
