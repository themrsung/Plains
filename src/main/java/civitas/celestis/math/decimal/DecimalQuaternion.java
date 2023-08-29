package civitas.celestis.math.decimal;

import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * An immutable decimal quaternion.
 *
 * @see Decimal4
 */
public class DecimalQuaternion extends Decimal4 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The identity quaternion. This will produce no rotation.
     */
    public static final DecimalQuaternion IDENTITY
            = new DecimalQuaternion(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

    //
    // Constructors
    //

    /**
     * Creates a new decimal quaternion.
     *
     * @param a The A component (the scalar part) of this quaternion
     * @param b The B component (the vector's A component) of this quaternion
     * @param c The C component (the vector's B component) of this quaternion
     * @param d The D component (the vector's C component) of this quaternion
     */
    public DecimalQuaternion(@Nonnull BigDecimal a, @Nonnull BigDecimal b, @Nonnull BigDecimal c, @Nonnull BigDecimal d) {
        super(a, b, c, d);
    }

    /**
     * Creates a new decimal quaternion.
     *
     * @param s The scalar part of this quaternion.
     * @param v The vector part of this quaternion
     */
    public DecimalQuaternion(@Nonnull BigDecimal s, @Nonnull Decimal3 v) {
        super(s, v.a(), v.b(), v.c());
    }

    /**
     * Creates a new decimal quaternion.
     *
     * @param components An array containing the components of this quaternion in ABCD order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public DecimalQuaternion(@Nonnull BigDecimal[] components) {
        super(components);
    }

    /**
     * Creates a new decimal quaternion.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public DecimalQuaternion(@Nonnull Tuple<? extends BigDecimal> t) {
        super(t);
    }

    /**
     * Creates a new decimal quaternion.
     *
     * @param v The vector of which to copy component values from
     */
    public DecimalQuaternion(@Nonnull Vector4 v) {
        super(v);
    }

    /**
     * Creates a new decimal quaternion.
     *
     * @param q The quaternion of which to copy component values from
     */
    public DecimalQuaternion(@Nonnull Quaternion q) {
        super(q.mapToObj(BigDecimal::valueOf));
    }

    //
    // Properties
    //

    /**
     * Returns the scalar part of this quaternion.
     *
     * @return The scalar part of this quaternion
     */
    @Nonnull
    public BigDecimal scalar() {
        return a;
    }

    /**
     * Returns the vector part of this quaternion.
     *
     * @return The vector part of this quaternion
     */
    @Nonnull
    public Decimal3 vector() {
        return new Decimal3(b, c, d);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this quaternion, then returns the resulting quaternion.
     *
     * @param s The scalar of which to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public DecimalQuaternion add(@Nonnull BigDecimal s) {
        return new DecimalQuaternion(a.add(s), b, c, d);
    }

    /**
     * Subtracts this quaternion by a scalar, then returns the resulting quaternion.
     *
     * @param s The scalar of which to subtract this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public DecimalQuaternion subtract(@Nonnull BigDecimal s) {
        return new DecimalQuaternion(a.subtract(s), b, c, d);
    }

    /**
     * Scales the rotation of this quaternion, then returns the resulting quaternion.
     * This will only work properly if this quaternion is a rotation quaternion.
     * (a quaternion with a Euclidean norm of {@code 1})
     *
     * @param s The scale factor to apply to the rotation
     * @return The scaled quaternion
     */
    @Nonnull
    public DecimalQuaternion scale(@Nonnull BigDecimal s) {
        if (a.compareTo(BigDecimal.ONE) >= 0) {
            return IDENTITY;
        }

        final double halfAngle = Math.acos(a.doubleValue());
        final double newHalfAngle = halfAngle * s.doubleValue();

        final double sinHalfAngle = Math.sin(halfAngle);
        final double sinNewHalfAngle = Math.sin(newHalfAngle);

        final BigDecimal scaleFactor = BigDecimal.valueOf(sinNewHalfAngle)
                .divide(BigDecimal.valueOf(sinHalfAngle), Decimals.RUNTIME_CONTEXT);

        return new DecimalQuaternion(
                BigDecimal.valueOf(Math.cos(newHalfAngle)),
                b.multiply(scaleFactor),
                c.multiply(scaleFactor),
                d.multiply(scaleFactor)
        );
    }

    /**
     * Divides this quaternion by a scalar, then returns the resulting quaternion.
     *
     * @param s The scalar of which to divide each component of this quaternion by
     * @return The resulting quaternion
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    @Override
    public DecimalQuaternion divide(@Nonnull BigDecimal s) throws ArithmeticException {
        return new DecimalQuaternion(
                a.divide(s, Decimals.RUNTIME_CONTEXT),
                b.divide(s, Decimals.RUNTIME_CONTEXT),
                c.divide(s, Decimals.RUNTIME_CONTEXT),
                d.divide(s, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * Adds another quaternion to this quaternion, then returns the resulting quaternion.
     *
     * @param q The quaternion of which to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    public DecimalQuaternion add(@Nonnull DecimalQuaternion q) {
        return new DecimalQuaternion(a.add(q.a), b.add(q.b), c.add(q.c), d.add(q.d));
    }

    /**
     * Subtracts this quaternion by another quaternion, then returns the resulting quaternion.
     *
     * @param q The quaternion of which to subtract from this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    public DecimalQuaternion subtract(@Nonnull DecimalQuaternion q) {
        return new DecimalQuaternion(a.subtract(q.a), b.subtract(q.b), c.subtract(q.c), d.subtract(q.d));
    }


    /**
     * Multiplies this quaternion by the provided quaternion {@code q}.
     *
     * @param q The quaternion of which to multiply this quaternion by
     * @return The quaternion left-product of the two quaternions
     */
    @Nonnull
    public DecimalQuaternion multiply(@Nonnull DecimalQuaternion q) {
        return new DecimalQuaternion(
                a.multiply(q.a).subtract(b.multiply(q.b)).subtract(c.multiply(q.c)).subtract(d.multiply(q.d)),
                (a.multiply(q.b)).add(b.multiply(q.a)).add(c.multiply(q.d)).subtract(d.multiply(q.c)),
                (a.multiply(q.c)).subtract(b.multiply(q.d)).add(c.multiply(q.a)).add(d.multiply(q.b)),
                (a.multiply(q.d)).add(b.multiply(q.c)).subtract(c.multiply(q.b)).add(d.multiply(q.a))
        );
    }

    //
    // Conjugation & Inversion
    //

    /**
     * Returns the conjugate of this quaternion.
     *
     * @return The conjugate of this quaternion
     */
    @Nonnull
    public DecimalQuaternion conjugate() {
        return new DecimalQuaternion(a, b.negate(), c.negate(), d.negate());
    }

    /**
     * Returns the inverse of this quaternion.
     *
     * @return The inverse of this quaternion
     * @throws ArithmeticException When the squared Euclidean norm (the squared magnitude)
     *                             of this quaternion is zero
     */
    @Nonnull
    public DecimalQuaternion inverse() throws ArithmeticException {
        return conjugate().divide(norm2());
    }

    //
    // Negation
    //

    /**
     * Negates this quaternion, then returns the negated quaternion.
     *
     * @return The negation of this quaternion
     */
    @Nonnull
    @Override
    public DecimalQuaternion negate() {
        return new DecimalQuaternion(a.negate(), b.negate(), c.negate(), d.negate());
    }

    //
    // Normalization
    //

    /**
     * Normalizes this quaternion to have a Euclidean norm (magnitude) of {@code 1}.
     *
     * @return The normalized quaternion of this quaternion
     * @throws ArithmeticException When the Euclidean norm of this quaternion is zero
     */
    @Nonnull
    @Override
    public DecimalQuaternion normalize() throws ArithmeticException {
        final BigDecimal n = a.multiply(a).add(b.multiply(b)).add(c.multiply(c)).add(d.multiply(d))
                .sqrt(Decimals.RUNTIME_CONTEXT);

        return new DecimalQuaternion(
                a.divide(n, Decimals.RUNTIME_CONTEXT),
                b.divide(n, Decimals.RUNTIME_CONTEXT),
                c.divide(n, Decimals.RUNTIME_CONTEXT),
                d.divide(n, Decimals.RUNTIME_CONTEXT)
        );
    }

    /**
     * Normalizes this quaternion to have a Euclidean norm (magnitude) of {@code 1}. If this quaternion
     * has no direction (the Euclidean norm is zero), this will the identity quaternion. ({@link #IDENTITY})
     *
     * @return The normalized quaternion of this quaternion if successful,
     * {@link #IDENTITY the identity quaternion} otherwise
     */
    @Nonnull
    public DecimalQuaternion normalizeOrIdentity() {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return IDENTITY;
        }
    }

    /**
     * Normalizes this quaternion to have a Euclidean norm (magnitude) of {@code 1}. If this quaternion
     * has no direction (the Euclidean norm is zero), this will return the provided fallback value
     * {@code q} instead of throwing an exception.
     *
     * @param q The fallback value to default to when normalization is impossible
     * @return The normalized quaternion of this quaternion if successful, the fallback value otherwise
     */
    @Nonnull
    public DecimalQuaternion normalizeOrDefault(@Nonnull DecimalQuaternion q) {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return q;
        }
    }
}
