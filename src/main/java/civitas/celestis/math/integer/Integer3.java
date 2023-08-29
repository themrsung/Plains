package civitas.celestis.math.integer;

import civitas.celestis.math.decimal.Decimal3;
import civitas.celestis.math.decimal.Decimals;
import civitas.celestis.util.tuple.Int3;
import civitas.celestis.util.tuple.Long3;
import civitas.celestis.util.tuple.Object3;
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
 * An immutable three-dimensional {@link BigInteger} vector.
 *
 * @see IntegerVector
 */
public class Integer3 extends Object3<BigInteger> implements IntegerVector<Integer3, Decimal3, Long3> {
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
    public static final Integer3 ZERO = new Integer3(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);

    /**
     * The positive A unit vector.
     */
    public static final Integer3 POSITIVE_A = new Integer3(BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO);

    /**
     * The positive B unit vector.
     */
    public static final Integer3 POSITIVE_B = new Integer3(BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO);

    /**
     * The positive C unit vector.
     */
    public static final Integer3 POSITIVE_C = new Integer3(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE);

    /**
     * The negative A unit vector.
     */
    public static final Integer3 NEGATIVE_A = new Integer3(BigInteger.ONE.negate(), BigInteger.ZERO, BigInteger.ZERO);

    /**
     * The negative B unit vector.
     */
    public static final Integer3 NEGATIVE_B = new Integer3(BigInteger.ZERO, BigInteger.ONE.negate(), BigInteger.ZERO);

    /**
     * The negative C unit vector.
     */
    public static final Integer3 NEGATIVE_C = new Integer3(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE.negate());

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param a The A component of this vector
     * @param b The B component of this vector
     * @param c The C component of this vector
     */
    public Integer3(@Nonnull BigInteger a, @Nonnull BigInteger b, @Nonnull BigInteger c) {
        super(a, b, c);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in ABC order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Integer3(@Nonnull BigInteger[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Integer3(@Nonnull Tuple<? extends BigInteger> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public Integer3(@Nonnull Long3 t) {
        super(t.mapToObj(BigInteger::valueOf));
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
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
        return new BigDecimal(a.multiply(a).add(b.multiply(b)).add(c.multiply(c)))
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
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger normManhattan() {
        return a.abs().add(b.abs()).add(c.abs());
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
    public Integer3 add(@Nonnull BigInteger s) {
        return new Integer3(a.add(s), b.add(s), c.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 subtract(@Nonnull BigInteger s) {
        return new Integer3(a.subtract(s), b.subtract(s), c.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 multiply(@Nonnull BigInteger s) {
        return new Integer3(a.multiply(s), b.multiply(s), c.multiply(s));
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
    public Integer3 divide(@Nonnull BigInteger s) throws ArithmeticException {
        return new Integer3(a.divide(s), b.divide(s), c.divide(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 add(@Nonnull Integer3 v) {
        return new Integer3(a.add(v.a), b.add(v.b), c.add(v.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 subtract(@Nonnull Integer3 v) {
        return new Integer3(a.subtract(v.a), b.subtract(v.b), c.subtract(v.c));
    }

    /**
     * Returns the cross product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the cross product between
     * @return The cross product between the two vectors
     */
    @Nonnull
    public Integer3 cross(@Nonnull Integer3 v) {
        return new Integer3(
                b.multiply(v.c).subtract(c.multiply(v.b)),
                c.multiply(v.a).subtract(a.subtract(v.c)),
                a.multiply(v.b).subtract(b.multiply(v.a))
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger dot(@Nonnull Integer3 v) {
        return a.multiply(v.a).add(b.multiply(v.b)).add(c.multiply(v.c));
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
    public Decimal3 normalize() throws ArithmeticException {
        final BigDecimal n = norm();
        return new Decimal3(
                new BigDecimal(a).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(b).divide(n, Decimals.RUNTIME_CONTEXT),
                new BigDecimal(c).divide(n, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal3 normalizeOrZero() {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return Decimal3.ZERO;
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
    public Decimal3 normalizeOrDefault(@Nonnull Decimal3 v) {
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
    public Integer3 negate() {
        return new Integer3(a.negate(), b.negate(), c.negate());
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
    public Integer3 min(@Nonnull Integer3 v) {
        return new Integer3(a.min(v.a), b.min(v.b), c.min(v.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 max(@Nonnull Integer3 v) {
        return new Integer3(a.max(v.a), b.max(v.b), c.max(v.c));
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
    public Integer3 clamp(@Nonnull Integer3 min, @Nonnull Integer3 max) {
        return new Integer3(
                Integers.clamp(a, min.a, max.a),
                Integers.clamp(b, min.b, max.b),
                Integers.clamp(c, min.c, max.c)
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
    public BigDecimal distance(@Nonnull Integer3 v) {
        final BigDecimal da = new BigDecimal(a.subtract(v.a));
        final BigDecimal db = new BigDecimal(b.subtract(v.b));
        final BigDecimal dc = new BigDecimal(c.subtract(v.c));


        return da.multiply(da).add(db.multiply(db)).add(dc.multiply(dc))
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
    public BigInteger distance2(@Nonnull Integer3 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);
        final BigInteger dc = c.subtract(v.c);

        return da.multiply(da).add(db.multiply(db)).add(dc.multiply(dc));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigInteger distanceManhattan(@Nonnull Integer3 v) {
        final BigInteger da = a.subtract(v.a);
        final BigInteger db = b.subtract(v.b);
        final BigInteger dc = c.subtract(v.c);

        return da.abs().add(db.abs()).add(dc.abs());
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
    public Integer3 transform(@Nonnull UnaryOperator<BigInteger> f) {
        return new Integer3(f.apply(a), f.apply(b), f.apply(c));
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
    public Integer3 merge(@Nonnull Integer3 v, @Nonnull BinaryOperator<BigInteger> f) {
        return new Integer3(f.apply(a, v.a), f.apply(b, v.b), f.apply(c, v.c));
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
    public Long3 primValue() {
        return new Long3(a.longValue(), b.longValue(), c.longValue());
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
    public boolean equals(@Nullable Integer3 v) {
        return v != null && a.equals(v.a) && b.equals(v.b) && c.equals(v.c);
    }
}
