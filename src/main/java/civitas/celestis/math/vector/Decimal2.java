package civitas.celestis.math.vector;

import civitas.celestis.math.Decimals;
import civitas.celestis.util.group.ArrayTuple;
import civitas.celestis.util.group.Pair;
import civitas.celestis.util.group.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

/**
 * An AB-notated two-dimensional vector which uses {@link BigDecimal} to represent its components.
 */
public class Decimal2 extends Pair<BigDecimal> implements DecimalVector<Decimal2> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -5773551455938188527L;

    /**
     * A vector with no direction or magnitude. Represents origin.
     */
    public static final Decimal2 ZERO = new Decimal2(BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * The positive A unit vector.
     */
    public static final Decimal2 POSITIVE_A = new Decimal2(BigDecimal.ONE, BigDecimal.ZERO);

    /**
     * The positive B unit vector.
     */
    public static final Decimal2 POSITIVE_B = new Decimal2(BigDecimal.ZERO, BigDecimal.ONE);

    /**
     * The negative A unit vector.
     */
    public static final Decimal2 NEGATIVE_A = new Decimal2(BigDecimal.ONE.negate(), BigDecimal.ZERO);

    /**
     * The negative B unit vector.
     */
    public static final Decimal2 NEGATIVE_B = new Decimal2(BigDecimal.ZERO, BigDecimal.ONE.negate());

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
    }

    /**
     * Creates a new vector.
     *
     * @param values An array containing the components of this vector in AB order
     */
    public Decimal2(@Nonnull BigDecimal[] values) {
        super(new ArrayTuple<>(values));
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public Decimal2(@Nonnull Tuple<BigDecimal> t) {
        super(t);
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal2(@Nonnull Vector<?, ?> v) {
        this(v.collect().stream().map(n -> BigDecimal.valueOf(n.doubleValue())).toArray(BigDecimal[]::new));
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal2(@Nonnull DecimalVector<?> v) {
        this(v.array());
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Decimal2(@Nonnull Decimal2 v) {
        super(v);
    }

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return a.equals(BigDecimal.ZERO) && b.equals(BigDecimal.ZERO);
    }

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
     * @param n The scalar to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 add(@Nonnull BigDecimal n) {
        return new Decimal2(a.add(n), b.add(n));
    }

    /**
     * {@inheritDoc}
     *
     * @param n The scalar to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 subtract(@Nonnull BigDecimal n) {
        return new Decimal2(a.subtract(n), b.subtract(n));
    }

    /**
     * {@inheritDoc}
     *
     * @param n The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 multiply(@Nonnull BigDecimal n) {
        return new Decimal2(a.multiply(n), b.multiply(n));
    }

    /**
     * {@inheritDoc}
     *
     * @param n The scalar to divide this vector by
     * @return {@inheritDoc}
     * @throws ArithmeticException When the denominator {@code n} is zero
     */
    @Nonnull
    @Override
    public Decimal2 divide(@Nonnull BigDecimal n) throws ArithmeticException {
        return new Decimal2(a.divide(n, Decimals.RUNTIME_CONTEXT), b.divide(n, Decimals.RUNTIME_CONTEXT));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to add to this vector
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
     * @param v The vector to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 subtract(@Nonnull Decimal2 v) {
        return new Decimal2(a.subtract(v.a), b.subtract(v.b));
    }

    /**
     * Multiplies this vector by another vector using complex number multiplication.
     *
     * @param v The vector to multiply this vector by
     * @return The complex number product between the two vectors
     */
    @Nonnull
    public Decimal2 multiply(@Nonnull Decimal2 v) {
        return new Decimal2(
                a.multiply(v.a).subtract(b.multiply(v.b)),
                a.multiply(v.b).add(b.multiply(v.a))
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
    public BigDecimal dot(@Nonnull Decimal2 v) {
        return a.multiply(v.a).add(b.multiply(v.b));
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
    public Decimal2 normalize() {
        final BigDecimal n = norm();
        return new Decimal2(a.divide(n, Decimals.RUNTIME_CONTEXT), b.divide(n, Decimals.RUNTIME_CONTEXT));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 normalizeOrZero() {
        final BigDecimal n = norm();
        try {
            return new Decimal2(a.divide(n, Decimals.RUNTIME_CONTEXT), b.divide(n, Decimals.RUNTIME_CONTEXT));
        } catch (final ArithmeticException e) {
            return ZERO;
        }
    }

    //
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the distance to
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
     * @param v The vector of which to get the squared distance to
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
     * @param v The vector of which to get the distance to
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
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 clamp(@Nonnull Decimal2 min, @Nonnull Decimal2 max) {
        return new Decimal2(Decimals.clamp(a, min.a, max.a), Decimals.clamp(b, min.b, max.b));
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Decimal2 transform(@Nonnull Function<? super BigDecimal, BigDecimal> f) {
        return new Decimal2(f.apply(a), f.apply(b));
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector counter-clockwise by given angle.
     *
     * @param angRads The angle of rotation to apply in radians
     */
    @Nonnull
    public Decimal2 rotate(double angRads) {
        final BigDecimal cos = BigDecimal.valueOf(Math.cos(angRads));
        final BigDecimal sin = BigDecimal.valueOf(Math.sin(angRads));

        return new Decimal2(
                cos.multiply(a).subtract(sin.multiply(b)),
                sin.multiply(a).add(cos.multiply(b))
        );
    }

    //
    // Type Conversion
    //

    /**
     * This is already a group, and thus there is no need for conversion.
     * This simply returns a reference to itself.
     *
     * @return {@code this}
     */
    @Nonnull
    @Override
    public Decimal2 group() {
        return this;
    }

    //
    // Numeric Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 toDouble() {
        return new Vector2(a.doubleValue(), b.doubleValue());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The conversion function to apply to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 toDouble(@Nonnull Function<? super BigDecimal, Double> f) {
        return new Vector2(f.apply(a), f.apply(b));
    }


    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Decimal3 v) {
            return equals(v);
        }

        if (obj instanceof DecimalVector<?> v) {
            return Arrays.equals(array(), v.array());
        }

        if (obj instanceof Vector<?, ?> v) {
            final BigDecimal[] a1 = array();
            final Number[] a2 = v.collect().toArray(Number[]::new);

            if (a2.length != 2) return false;

            for (int i = 0; i < 2; i++) {
                if (a1[i].doubleValue() != a2[i].doubleValue()) return false;
            }

            return true;
        }

        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Decimal2 v) {
        if (v == null) return false;
        return a.equals(v.a) && b.equals(v.b);
    }

    //
    // Serialization
    //

    /**
     * Deserializes a vector into a string.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Decimal2 parseVector(@Nonnull String input) throws NumberFormatException {
        final String[] split = input.trim().replaceAll("[\\[]]", "").split(", ");

        if (split.length != 2) {
            throw new NumberFormatException("The provided string does not have two components.");
        }

        final BigDecimal[] values = new BigDecimal[2];

        for (int i = 0; i < 2; i++) {
            values[i] = new BigDecimal(split[i]);
        }

        return new Decimal2(values);
    }
}
