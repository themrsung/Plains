package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal2;
import civitas.celestis.util.tuple.Int2;
import civitas.celestis.util.tuple.Long2;
import civitas.celestis.util.tuple.Object2;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.math.BigInteger;
import java.util.Objects;

public abstract class Integer2 extends Object2<BigInteger> implements IntegerVector<Integer2, Decimal2, Long2> {
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

    public Integer2(@Nonnull BigInteger a, @Nonnull BigInteger b) {
        super(a, b);
        requireNonNull();
    }

    public Integer2(@Nonnull BigInteger[] elements) {
        super(elements);
        requireNonNull();
    }

    public Integer2(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    public Integer2(@Nonnull Long2 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    public Integer2(@Nonnull Int2 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    /**
     * Requires that all components of this vector are non-null.
     */
    protected final void requireNonNull() {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
    }
}
