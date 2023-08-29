package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal3;
import civitas.celestis.util.tuple.Int3;
import civitas.celestis.util.tuple.Long3;
import civitas.celestis.util.tuple.Object3;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.math.BigInteger;
import java.util.Objects;

public abstract class Integer3 extends Object3<BigInteger> implements IntegerVector<Integer3, Decimal3, Long3> {
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

    public Integer3(@Nonnull BigInteger a, @Nonnull BigInteger b, @Nonnull BigInteger c) {
        super(a, b, c);
        requireNonNull();
    }

    public Integer3(@Nonnull BigInteger[] elements) {
        super(elements);
        requireNonNull();
    }

    public Integer3(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    public Integer3(@Nonnull Long3 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    public Integer3(@Nonnull Int3 t) {
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
    }
}
