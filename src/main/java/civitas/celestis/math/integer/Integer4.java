package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal4;
import civitas.celestis.math.decimal.Decimals;
import civitas.celestis.util.tuple.Int4;
import civitas.celestis.util.tuple.Long4;
import civitas.celestis.util.tuple.Object4;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * An immutable four-dimensional {@link BigInteger} vector.
 *
 * @see IntegerVector
 */
public class Integer4 extends Object4<BigInteger> implements IntegerVector<Integer4, Decimal4, Long4> {
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

    /**
     * Creates a new vector.
     *
     * @param a The A component of this vector
     * @param b The B component of this vector
     * @param c The C component of this vector
     * @param d The D component of this vector
     */
    public Integer4(@Nonnull BigInteger a, @Nonnull BigInteger b, @Nonnull BigInteger c, @Nonnull BigInteger d) {
        super(a, b, c, d);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the component of this vector in ABCD order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public Integer4(@Nonnull BigInteger[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Integer4(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public Integer4(@Nonnull Long4 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
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

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm() {
        return new BigDecimal(a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d)))
                .sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger norm2() {
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger normManhattan() {
        return a.abs().add(b.abs()).add(c.abs()).add(d.abs());
    }

    //
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to add to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 add(@Nonnull BigInteger s) {
        return new Integer4(a.add(s), b.add(s), c.add(s), d.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 subtract(@Nonnull BigInteger s) {
        return new Integer4(a.subtract(s), b.subtract(s), c.subtract(s), d.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 multiply(@Nonnull BigInteger s) {
        return new Integer4(a.multiply(s), b.multiply(s), c.multiply(s), d.multiply(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to divide each component of this vector by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 divide(@Nonnull BigInteger s) throws ArithmeticException {
        return new Integer4(a.divide(s), b.divide(s), c.divide(s), d.divide(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 add(@Nonnull Integer4 v) {
        return new Integer4(a.add(v.a), b.add(v.b), c.add(v.c), d.add(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 subtract(@Nonnull Integer4 v) {
        return new Integer4(a.subtract(v.a), b.subtract(v.b), c.subtract(v.c), d.subtract(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger dot(@Nonnull Integer4 v) {
        return a.multiply(v.a).add(b.multiply(v.b)).add(c.multiply(v.c)).add(d.multiply(v.d));
    }

    //
    // Normalization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 normalize() throws ArithmeticException {
        final BigDecimal n = norm();
        return new Decimal4(
                new BigDecimal(a).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(b).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(c).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(d).divide(n, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 normalizeOrZero() {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return Decimal4.ZERO;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to return in case of division by zero
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 normalizeOrDefault(@Nonnull Decimal4 v) {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return v;
        }
    }

    //
    // Negation
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 negate() {
        return new Integer4(a.negate(), b.negate(), c.negate(), d.negate());
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 min(@Nonnull Integer4 v) {
        return new Integer4(a.min(v.a), b.min(v.b), c.min(v.c), d.min(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 max(@Nonnull Integer4 v) {
        return new Integer4(a.max(v.a), b.max(v.b), c.max(v.c), d.max(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 clamp(@Nonnull Integer4 min, @Nonnull Integer4 max) {
        return new Integer4(
                Integers.clamp(a, min.a, max.a),
                Integers.clamp(b, min.b, max.b),
                Integers.clamp(c, min.c, max.c),
                Integers.clamp(d, min.d, max.d)
        );
    }

    //
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which get the Euclidean distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal distance(@Nonnull Integer4 v) {
        final BigDecimal da = new BigDecimal(a.subtract(v.a));
        final BigDecimal db = new BigDecimal(b.subtract(v.b));
        final BigDecimal dc = new BigDecimal(c.subtract(v.c));
        final BigDecimal dd = new BigDecimal(d.subtract(v.d));

        return da.multiply(da).add(db.multiply(db)).add(dc.multiply(dc)).add(dd.multiply(dd))
                .sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger distance2(@Nonnull Integer4 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);
        final BigInteger dc = c.subtract(v.c);
        final BigInteger dd = d.subtract(v.d);

        return da.multiply(da).add(db.multiply(db)).add(dc.multiply(dc)).add(dd.multiply(dd));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger distanceManhattan(@Nonnull Integer4 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);
        final BigInteger dc = c.subtract(v.c);
        final BigInteger dd = d.subtract(v.d);

        return da.abs().add(db.abs()).add(dc.abs()).add(dd.abs());
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 transform(@Nonnull UnaryOperator<BigInteger> f) {
        return new Integer4(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to merge this vector with
     * @param f The merger function to handle the merging of the two vectors
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 merge(@Nonnull Integer4 v, @Nonnull BinaryOperator<BigInteger> f) {
        return new Integer4(f.apply(a, v.a), f.apply(b, v.b), f.apply(c, v.c), f.apply(d, v.d));
    }

    //
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Long4 primValue() {
        return new Long4(a.longValue(), b.longValue(), c.longValue(), d.longValue());
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Integer4 v) {
        return v != null && a.equals(v.a) && b.equals(v.b) && c.equals(v.c) && d.equals(v.d);
    }
}
