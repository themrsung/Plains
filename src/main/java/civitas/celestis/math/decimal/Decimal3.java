package civitas.celestis.math.decimal;

import civitas.celestis.math.integer.Integer3;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.Object3;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * An immutable three-dimensional decimal vector.
 *
 * @see DecimalVector
 */
public class Decimal3 extends Object3<BigDecimal> implements DecimalVector<Decimal3, Vector3> {
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
    public static final Decimal3 ZERO = new Decimal3(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive A unit vector.
     */
    public static final Decimal3 POSITIVE_A = new Decimal3(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive B unit vector.
     */
    public static final Decimal3 POSITIVE_B = new Decimal3(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);

    /**
     * The positive C unit vector.
     */
    public static final Decimal3 POSITIVE_C = new Decimal3(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);

    /**
     * The negative A unit vector.
     */
    public static final Decimal3 NEGATIVE_A = new Decimal3(BigDecimal.ONE.negate(), BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The negative B unit vector.
     */
    public static final Decimal3 NEGATIVE_B = new Decimal3(BigDecimal.ZERO, BigDecimal.ONE.negate(), BigDecimal.ZERO);

    /**
     * The negative C unit vector.
     */
    public static final Decimal3 NEGATIVE_C = new Decimal3(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE.negate());

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
    public Decimal3(@Nonnull BigDecimal a, @Nonnull BigDecimal b, @Nonnull BigDecimal c) {
        super(a, b, c);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in ABC order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Decimal3(@Nonnull BigDecimal[] components) {
        super(components);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Decimal3(@Nonnull Tuple<? extends BigDecimal> t) {
        super(t);
        requireNonNull();
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal3(@Nonnull Vector3 v) {
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
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm2() {
        return a.multiply(a).add(b.multiply(b)).add(c.multiply(c));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal normManhattan() {
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
    public Decimal3 add(@Nonnull BigDecimal s) {
        return new Decimal3(a.add(s), b.add(s), c.add(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal3 subtract(@Nonnull BigDecimal s) {
        return new Decimal3(a.subtract(s), b.subtract(s), c.subtract(s));
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal3 multiply(@Nonnull BigDecimal s) {
        return new Decimal3(a.multiply(s), b.multiply(s), c.multiply(s));
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
    public Decimal3 divide(@Nonnull BigDecimal s) throws ArithmeticException {
        return new Decimal3(
                a.divide(s, Decimals.RUNTIME_CONTEXT),
                b.divide(s, Decimals.RUNTIME_CONTEXT),
                c.divide(s, Decimals.RUNTIME_CONTEXT)
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
    public Decimal3 add(@Nonnull Decimal3 v) {
        return new Decimal3(a.add(v.a), b.add(v.b), c.add(v.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal3 subtract(@Nonnull Decimal3 v) {
        return new Decimal3(a.subtract(v.a), b.subtract(v.b), c.subtract(v.c));
    }

    /**
     * Returns the cross product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the cross product between
     * @return The cross product between the two vectors
     */
    @Nonnull
    public Decimal3 cross(@Nonnull Decimal3 v) {
        return new Decimal3(
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
    public BigDecimal dot(@Nonnull Decimal3 v) {
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).sqrt(Decimals.RUNTIME_CONTEXT);
        return new Decimal3(
                a.divide(n, Decimals.RUNTIME_CONTEXT),
                b.divide(n, Decimals.RUNTIME_CONTEXT),
                c.divide(n, Decimals.RUNTIME_CONTEXT)
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
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).sqrt(Decimals.RUNTIME_CONTEXT);
        try {
            return new Decimal3(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT),
                    c.divide(n, Decimals.RUNTIME_CONTEXT)
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
    public Decimal3 normalizeOrDefault(@Nonnull Decimal3 v) {
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).sqrt(Decimals.RUNTIME_CONTEXT);
        try {
            return new Decimal3(
                    a.divide(n, Decimals.RUNTIME_CONTEXT),
                    b.divide(n, Decimals.RUNTIME_CONTEXT),
                    c.divide(n, Decimals.RUNTIME_CONTEXT)
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
    public Decimal3 negate() {
        return new Decimal3(a.negate(), b.negate(), c.negate());
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
    public Decimal3 min(@Nonnull Decimal3 v) {
        return new Decimal3(a.min(v.a), b.min(v.b), c.min(v.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal3 max(@Nonnull Decimal3 v) {
        return new Decimal3(a.max(v.a), b.max(v.b), c.max(v.c));
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
    public Decimal3 clamp(@Nonnull Decimal3 min, @Nonnull Decimal3 max) {
        return new Decimal3(
                Decimals.clamp(a, min.a, max.a),
                Decimals.clamp(b, min.b, max.b),
                Decimals.clamp(c, min.c, max.c)
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
    public BigDecimal distance(@Nonnull Decimal3 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);

        return da.multiply(da).add(db.multiply(db)).add(dc.multiply(dc)).sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal distance2(@Nonnull Decimal3 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);

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
    public BigDecimal distanceManhattan(@Nonnull Decimal3 v) {
        final BigDecimal da = a.subtract(v.a);
        final BigDecimal db = b.subtract(v.b);
        final BigDecimal dc = c.subtract(v.c);

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
    public Decimal3 transform(@Nonnull UnaryOperator<BigDecimal> f) {
        return new Decimal3(f.apply(a), f.apply(b), f.apply(c));
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
    public Decimal3 merge(@Nonnull Decimal3 v, @Nonnull BinaryOperator<BigDecimal> f) {
        return new Decimal3(f.apply(a, v.a), f.apply(b, v.b), f.apply(c, v.c));
    }

    //
    // Rotation
    //

    /**
     * Converts this vector into a pure quaternion. (a quaternion whose scalar part is {@code 0},
     * and the vector part is populated from this vector's components)
     *
     * @return A pure quaternion constructed from this vector
     */
    @Nonnull
    public DecimalQuaternion quaternion() {
        return new DecimalQuaternion(BigDecimal.ZERO, a, b, c);
    }

    /**
     * Rotates this vector by the provided rotation quaternion {@code q}.
     *
     * @param q The rotation quaternion of which to apply to this vector
     * @return The rotated vector
     */
    @Nonnull
    public Decimal3 rotate(@Nonnull DecimalQuaternion q) {
        return q.multiply(quaternion()).multiply(q.conjugate()).vector();
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
    public Vector3 primValue() {
        return new Vector3(a.doubleValue(), b.doubleValue(), c.doubleValue());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer3 bigIntegerValue() {
        return new Integer3(a.toBigInteger(), b.toBigInteger(), c.toBigInteger());
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
    public boolean equals(@Nullable Decimal3 v) {
        return v != null && a.equals(v.a) && b.equals(v.b) && c.equals(v.c);
    }
}
