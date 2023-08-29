package civitas.celestis.math.decimal;

import civitas.celestis.math.integer.Integer4;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.tuple.Object4;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * An immutable four-dimensional decimal vector.
 *
 * @see DecimalVector
 */
public class Decimal4 extends Object4<BigDecimal> implements DecimalVector<Decimal4, Vector4> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * A vector of no direction of magnitude. Represents origin.
     */

    public static final Decimal4 ZERO =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive A unit vector.
     */
    public static final Decimal4 POSITIVE_A =
            new Decimal4(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive B unit vector.
     */
    public static final Decimal4 POSITIVE_B =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive C unit vector.
     */
    public static final Decimal4 POSITIVE_C =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);

    /**
     * The positive D unit vector.
     */
    public static final Decimal4 POSITIVE_D =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);

    /**
     * The negative A unit vector.
     */
    public static final Decimal4 NEGATIVE_A =
            new Decimal4(BigDecimal.ONE.negate(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The negative B unit vector.
     */
    public static final Decimal4 NEGATIVE_B =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ONE.negate(), BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The negative C unit vector.
     */
    public static final Decimal4 NEGATIVE_C =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE.negate(), BigDecimal.ZERO);

    /**
     * The negative D unit vector.
     */
    public static final Decimal4 NEGATIVE_D =
            new Decimal4(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE.negate());

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
    public Decimal4(@Nonnull BigDecimal a, @Nonnull BigDecimal b, @Nonnull BigDecimal c, @Nonnull BigDecimal d) {
        super(a, b, c, d);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in ABCD order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Decimal4(@Nonnull BigDecimal[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Decimal4(@Nonnull Tuple<? extends BigDecimal> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal4(@Nonnull Vector4 v) {
        super(v.mapToObj(BigDecimal::valueOf));
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
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d))
                .sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm2() {
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal normManhattan() {
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
    public Decimal4 add(@Nonnull BigDecimal s) {
        return new Decimal4(a.add(s), b.add(s), c.add(s), d.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 subtract(@Nonnull BigDecimal s) {
        return new Decimal4(a.subtract(s), b.subtract(s), c.subtract(s), d.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 multiply(@Nonnull BigDecimal s) {
        return new Decimal4(a.multiply(s), b.multiply(s), c.multiply(s), d.multiply(s));
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
    public Decimal4 divide(@Nonnull BigDecimal s) throws ArithmeticException {
        return new Decimal4(
                a.divide(s, Decimals.RUNTIME_CONTEXT),
                b.divide(s, Decimals.RUNTIME_CONTEXT),
                c.divide(s, Decimals.RUNTIME_CONTEXT),
                d.divide(s, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 add(@Nonnull Decimal4 v) {
        return new Decimal4(a.add(v.a), b.add(v.b), c.add(v.c), d.add(v.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 subtract(@Nonnull Decimal4 v) {
        return new Decimal4(a.subtract(v.a), b.subtract(v.b), c.subtract(v.c), d.subtract(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal dot(@Nonnull Decimal4 v) {
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d))
                .sqrt(Decimals.RUNTIME_CONTEXT);

        return new Decimal4(
                a.divide(n, Decimals.RUNTIME_CONTEXT),
                b.divide(n, Decimals.RUNTIME_CONTEXT),
                c.divide(n, Decimals.RUNTIME_CONTEXT),
                d.divide(n, Decimals.RUNTIME_CONTEXT)
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d))
                .sqrt(Decimals.RUNTIME_CONTEXT);

        try {
            return new Decimal4(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT),
                    c.divide(n, Decimals.RUNTIME_CONTEXT),
                    d.divide(n, Decimals.RUNTIME_CONTEXT)
            );
        } catch (final ArithmeticException e) {
            return this;
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d))
                .sqrt(Decimals.RUNTIME_CONTEXT);

        try {
            return new Decimal4(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT),
                    c.divide(n, Decimals.RUNTIME_CONTEXT),
                    d.divide(n, Decimals.RUNTIME_CONTEXT)
            );
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
    public Decimal4 negate() {
        return new Decimal4(a.negate(), b.negate(), c.negate(), d.negate());
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
    public Decimal4 min(@Nonnull Decimal4 v) {
        return new Decimal4(a.min(v.a), b.min(v.b), c.min(v.c), d.min(v.d));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal4 max(@Nonnull Decimal4 v) {
        return new Decimal4(a.max(v.a), b.max(v.b), c.max(v.c), d.max(v.d));
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
    public Decimal4 clamp(@Nonnull Decimal4 min, @Nonnull Decimal4 max) {
        return new Decimal4(
                Decimals.clamp(a, min.a, max.a),
                Decimals.clamp(b, min.b, max.b),
                Decimals.clamp(c, min.c, max.c),
                Decimals.clamp(d, min.d, max.d)
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
    public BigDecimal distance(@Nonnull Decimal4 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);
        final BigDecimal dd = d.subtract(v.d);

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
    public BigDecimal distance2(@Nonnull Decimal4 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);
        final BigDecimal dd = d.subtract(v.d);

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
    public BigDecimal distanceManhattan(@Nonnull Decimal4 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);
        final BigDecimal dd = d.subtract(v.d);

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
    public Decimal4 transform(@Nonnull UnaryOperator<BigDecimal> f) {
        return new Decimal4(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
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
    public Decimal4 merge(@Nonnull Decimal4 v, @Nonnull BinaryOperator<BigDecimal> f) {
        return new Decimal4(f.apply(a, v.a), f.apply(b, v.b), f.apply(c, v.c), f.apply(d, v.d));
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
    public Vector4 primValue() {
        return new Vector4(a.doubleValue(), b.doubleValue(), c.doubleValue(), d.doubleValue());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer4 bigIntegerValue() {
        return new Integer4(a.toBigInteger(), b.toBigInteger(), c.toBigInteger(), d.toBigInteger());
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
    public boolean equals(@Nullable Decimal4 v) {
        return v != null && a.equals(v.a) && b.equals(v.b) && c.equals(v.c) && d.equals(v.d);
    }
}
