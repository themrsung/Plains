package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal2;
import civitas.celestis.math.decimal.Decimals;
import civitas.celestis.util.tuple.Int2;
import civitas.celestis.util.tuple.Long2;
import civitas.celestis.util.tuple.Object2;
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
 * An immutable two-dimensional {@link BigInteger} vector.
 *
 * @see IntegerVector
 */
public class Integer2 extends Object2<BigInteger> implements IntegerVector<Integer2, Decimal2, Long2> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * A vector of no direction or magnitude. Represents origin.
     */
    public static final Integer2 ZERO = new Integer2(BigInteger.ZERO, BigInteger.ZERO);

    /**
     * The positive A unit vector.
     */
    public static final Integer2 POSITIVE_A = new Integer2(BigInteger.ONE, BigInteger.ZERO);

    /**
     * The positive B unit vector.
     */
    public static final Integer2 POSITIVE_B = new Integer2(BigInteger.ZERO, BigInteger.ONE);

    /**
     * The negative A unit vector.
     */
    public static final Integer2 NEGATIVE_A = new Integer2(BigInteger.ONE.negate(), BigInteger.ZERO);

    /**
     * The negative B unit vector.
     */
    public static final Integer2 NEGATIVE_B = new Integer2(BigInteger.ZERO, BigInteger.ONE.negate());

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param a The A component of this vector
     * @param b The B component of this vector
     */
    public Integer2(@Nonnull BigInteger a, @Nonnull BigInteger b) {
        super(a, b);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in AB order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Integer2(@Nonnull BigInteger[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Integer2(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public Integer2(@Nonnull Long2 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
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
        return new BigDecimal(a.multiply(a).add(b.multiply(b)))
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
        return a.multiply(a).add(b.multiply(b));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger normManhattan() {
        return a.abs().add(b.abs());
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
    public Integer2 add(@Nonnull BigInteger s) {
        return new Integer2(a.add(s), b.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer2 subtract(@Nonnull BigInteger s) {
        return new Integer2(a.subtract(s), b.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer2 multiply(@Nonnull BigInteger s) {
        return new Integer2(a.multiply(s), b.multiply(s));
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
    public Integer2 divide(@Nonnull BigInteger s) throws ArithmeticException {
        return new Integer2(a.divide(s), b.divide(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer2 add(@Nonnull Integer2 v) {
        return new Integer2(a.add(v.a), b.add(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer2 subtract(@Nonnull Integer2 v) {
        return new Integer2(a.subtract(v.a), b.subtract(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger dot(@Nonnull Integer2 v) {
        return a.multiply(v.a).add(b.multiply(v.b));
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
    public Decimal2 normalize() throws ArithmeticException {
        final BigDecimal n = norm();
        return new Decimal2(
                new BigDecimal(a).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(b).divide(n, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 normalizeOrZero() {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return Decimal2.ZERO;
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
    public Decimal2 normalizeOrDefault(@Nonnull Decimal2 v) {
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
    public Integer2 negate() {
        return new Integer2(a.negate(), b.negate());
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
    public Integer2 min(@Nonnull Integer2 v) {
        return new Integer2(a.min(v.a), b.min(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer2 max(@Nonnull Integer2 v) {
        return new Integer2(a.max(v.a), b.max(v.b));
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
    public Integer2 clamp(@Nonnull Integer2 min, @Nonnull Integer2 max) {
        return new Integer2(
                Integers.clamp(a, min.a, max.a),
                Integers.clamp(b, min.b, max.b)
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
    public BigDecimal distance(@Nonnull Integer2 v) {
        final BigDecimal da = new BigDecimal(a.subtract(v.a));
        final BigDecimal db = new BigDecimal(b.subtract(v.b));

        return da.multiply(da).add(db.multiply(db)).sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger distance2(@Nonnull Integer2 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);

        return da.multiply(da).add(db.multiply(db));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger distanceManhattan(@Nonnull Integer2 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);

        return da.abs().add(db.abs());
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
    public Integer2 transform(@Nonnull UnaryOperator<BigInteger> f) {
        return new Integer2(f.apply(a), f.apply(b));
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
    public Integer2 merge(@Nonnull Integer2 v, @Nonnull BinaryOperator<BigInteger> f) {
        return new Integer2(f.apply(a, v.a), f.apply(b, v.b));
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
    public Long2 primValue() {
        return new Long2(a.longValue(), b.longValue());
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
    public boolean equals(@Nullable Integer2 v) {
        return v != null && a.equals(v.a) && b.equals(v.b);
    }
}
