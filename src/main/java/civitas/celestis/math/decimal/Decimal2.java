package civitas.celestis.math.decimal;

import civitas.celestis.math.vector.Vector2;
import civitas.celestis.util.tuple.Object2;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * An immutable two-dimensional decimal vector.
 *
 * @see DecimalVector
 */
public class Decimal2 extends Object2<BigDecimal> implements DecimalVector<Decimal2, Vector2> {
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
     */
    public Decimal2(@Nonnull BigDecimal a, @Nonnull BigDecimal b) {
        super(a, b);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in AB order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Decimal2(@Nonnull BigDecimal[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Decimal2(@Nonnull Tuple<? extends BigDecimal> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal2(@Nonnull Vector2 v) {
        super(v.mapToObj(BigDecimal::valueOf));
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
        return a.multiply(a).add(b.multiply(b)).sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm2() {
        return a.multiply(a).add(b.multiply(b));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal normManhattan() {
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
    public Decimal2 add(@Nonnull BigDecimal s) {
        return new Decimal2(a.add(s), b.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 subtract(@Nonnull BigDecimal s) {
        return new Decimal2(a.subtract(s), b.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 multiply(@Nonnull BigDecimal s) {
        return new Decimal2(a.multiply(s), b.multiply(s));
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
    public Decimal2 divide(@Nonnull BigDecimal s) throws ArithmeticException {
        return new Decimal2(
                a.divide(s, Decimals.RUNTIME_CONTEXT),
                b.divide(s, Decimals.RUNTIME_CONTEXT)
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
    public Decimal2 add(@Nonnull Decimal2 v) {
        return new Decimal2(a.add(v.a), b.add(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 subtract(@Nonnull Decimal2 v) {
        return new Decimal2(a.subtract(v.a), b.subtract(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal dot(@Nonnull Decimal2 v) {
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).sqrt(Decimals.RUNTIME_CONTEXT);
        return new Decimal2(
                a.divide(n, Decimals.RUNTIME_CONTEXT),
                b.divide(n, Decimals.RUNTIME_CONTEXT)
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).sqrt(Decimals.RUNTIME_CONTEXT);
        try {
            return new Decimal2(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT)
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
    public Decimal2 normalizeOrDefault(@Nonnull Decimal2 v) {
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).sqrt(Decimals.RUNTIME_CONTEXT);
        try {
            return new Decimal2(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT)
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
    public Decimal2 negate() {
        return new Decimal2(a.negate(), b.negate());
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
    public Decimal2 min(@Nonnull Decimal2 v) {
        return new Decimal2(a.min(v.a), b.min(v.b));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 max(@Nonnull Decimal2 v) {
        return new Decimal2(a.max(v.a), b.max(v.b));
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
    public Decimal2 clamp(@Nonnull Decimal2 min, @Nonnull Decimal2 max) {
        return new Decimal2(Decimals.clamp(a, min.a, max.a), Decimals.clamp(b, min.b, max.b));
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
    public BigDecimal distance(@Nonnull Decimal2 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);

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
    public BigDecimal distance2(@Nonnull Decimal2 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);

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
    public BigDecimal distanceManhattan(@Nonnull Decimal2 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);

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
    public Decimal2 transform(@Nonnull UnaryOperator<BigDecimal> f) {
        return new Decimal2(f.apply(a), f.apply(b));
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
    public Decimal2 merge(@Nonnull Decimal2 v, @Nonnull BinaryOperator<BigDecimal> f) {
        return new Decimal2(f.apply(a, v.a), f.apply(b, v.b));
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
    public Vector2 primValue() {
        return new Vector2(a.doubleValue(), b.doubleValue());
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
    public boolean equals(@Nullable Decimal2 v) {
        return v != null && a.equals(v.a) && b.equals(v.b);
    }
}
