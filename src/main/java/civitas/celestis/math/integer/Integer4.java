package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal4;
import civitas.celestis.util.tuple.Int4;
import civitas.celestis.util.tuple.Long4;
import civitas.celestis.util.tuple.Object4;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.math.BigInteger;
import java.util.Objects;

public abstract class Integer4 extends Object4<BigInteger> implements IntegerVector<Integer4, Decimal4, Long4> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    public Integer4(@Nonnull BigInteger a, @Nonnull BigInteger b, @Nonnull BigInteger c, @Nonnull BigInteger d) {
        super(a, b, c, d);
        requireNonNull();
    }

    public Integer4(@Nonnull BigInteger[] elements) {
        super(elements);
        requireNonNull();
    }

    public Integer4(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    public Integer4(@Nonnull Long4 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    public Integer4(@Nonnull Int4 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    /**
     * Requires that all components of this vector are non-null.
     */
    protected final void requireNonNull() {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        Objects.requireNonNull(c);
        Objects.requireNonNull(d);
    }
}
