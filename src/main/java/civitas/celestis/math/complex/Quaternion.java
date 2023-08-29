package civitas.celestis.math.complex;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.Double3;
import civitas.celestis.util.tuple.Double4;
import civitas.celestis.util.tuple.DoubleTuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A complex number with three imaginary parts. Quaternions are used to
 * describe the rotational motion of three-dimensional vectors.
 *
 * @see Double4
 */
public class Quaternion extends Double4 {
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
    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);

    //
    // Constructors
    //

    /**
     * Creates a new quaternion.
     *
     * @param w The W component of this quaternion
     * @param x The X component of this quaternion
     * @param y The Y component of this quaternion
     * @param z The Z component of this quaternion
     */
    public Quaternion(double w, double x, double y, double z) {
        super(w, x, y, z);
    }

    /**
     * Creates a new quaternion.
     *
     * @param s The scalar part of this quaternion
     * @param v The vector part of this quaternion
     */
    public Quaternion(double s, @Nonnull Double3 v) {
        super(s, v.x(), v.y(), v.z());
    }

    /**
     * Creates a new quaternion.
     *
     * @param components An array containing the component of this quaternion in WXYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public Quaternion(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new quaternion.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Quaternion(@Nonnull DoubleTuple t) {
        super(t);
    }

    //
    // Properties
    //

    /**
     * Returns the Euclidean norm (the magnitude) of this quaternion.
     *
     * @return The Euclidean norm of this quaternion
     */
    public double norm() {
        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * Returns the squared Euclidean norm (the squared magnitude) of this quaternion.
     *
     * @return The squared Euclidean norm of this quaternion
     */
    public double norm2() {
        return w * w + x * x + y * y + z * z;
    }

    /**
     * Returns the scalar part of this quaternion.
     *
     * @return The scalar part of this quaternion
     */
    public double scalar() {
        return w;
    }

    /**
     * Returns the vector part of this quaternion.
     *
     * @return The vector part of this quaternion
     */
    @Nonnull
    public Vector3 vector() {
        return new Vector3(x, y, z);
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
    public Quaternion add(double s) {
        return new Quaternion(w + s, x, y, z);
    }

    /**
     * Subtracts this quaternion by a scalar, then returns the resulting quaternion.
     *
     * @param s The scalar of which to subtract this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    public Quaternion subtract(double s) {
        return new Quaternion(w - s, x, y, z);
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
    public Quaternion scale(double s) {
        if (Math.abs(w() - 1) < Scalars.EPSILON) {
            return IDENTITY;
        }

        final double halfAngle = Math.acos(w);
        final double newHalfAngle = halfAngle * s;

        final double sinHalfAngle = Math.sin(halfAngle);
        final double sinNewHalfAngle = Math.sin(newHalfAngle);

        final double scaleFactor = sinNewHalfAngle / sinHalfAngle;

        return new Quaternion(
                Math.cos(newHalfAngle),
                x * scaleFactor,
                y * scaleFactor,
                z * scaleFactor
        );
    }

    /**
     * Multiplies this quaternion by a scalar, then returns the resulting quaternion.
     *
     * @param s The scalar of which to multiply this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    public Quaternion multiply(double s) {
        return new Quaternion(w * s, x * s, y * s, z * s);
    }

    /**
     * Divides this quaternion by a scalar, then returns the resulting quaternion.
     *
     * @param s The scalar of which to divide this quaternion by
     * @return The resulting quaternion
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    public Quaternion divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * Adds another quaternion to this quaternion, then returns the resulting quaternion.
     *
     * @param q The quaternion of which to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    public Quaternion add(@Nonnull Quaternion q) {
        return new Quaternion(w + q.w, x + q.x, y + q.y, z + q.z);
    }

    /**
     * Subtracts this quaternion by another quaternion, then returns the resulting quaternion.
     *
     * @param q The quaternion of which to subtract from this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    public Quaternion subtract(@Nonnull Quaternion q) {
        return new Quaternion(w - q.w, x - q.x, y - q.y, z - q.z);
    }

    /**
     * Multiplies this quaternion by the provided quaternion {@code q}.
     *
     * @param q The quaternion of which to multiply this quaternion by
     * @return The quaternion left-product of the two quaternions
     */
    @Nonnull
    public Quaternion multiply(@Nonnull Quaternion q) {
        return new Quaternion(
                w * q.w - x * q.x - y * q.y - z * q.z,
                w * q.x + x * q.w + y * q.z - z * q.y,
                w * q.y - x * q.z + y * q.w + z * q.x,
                w * q.z + x * q.y - y * q.x + z * q.w
        );
    }

    /**
     * Returns the dot product between this quaternion and the provided quaternion {@code q}.
     *
     * @param q The quaternion of which to get the dot product between
     * @return The dot product of the two quaternions
     */
    public double dot(@Nonnull Quaternion q) {
        return w * q.w + x * q.x + y * q.y + z * q.z;
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
    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    /**
     * Returns the inverse of this quaternion.
     *
     * @return The inverse of this quaternion
     * @throws ArithmeticException When the squared Euclidean norm (the squared magnitude)
     *                             of this quaternion is zero
     */
    @Nonnull
    public Quaternion inverse() throws ArithmeticException {
        final double n2 = norm2();
        if (n2 == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / n2, -x / n2, -y / n2, -z / n2);
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
    public Quaternion negate() {
        return new Quaternion(-w, -x, -y, -z);
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
    public Quaternion normalize() throws ArithmeticException {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * Normalizes this quaternion to have a Euclidean norm (magnitude) of {@code 1}. If this quaternion
     * has no direction (the Euclidean norm is zero), this will the identity quaternion. ({@link #IDENTITY})
     *
     * @return The normalized quaternion of this quaternion if successful,
     * {@link #IDENTITY the identity quaternion} otherwise
     */
    @Nonnull
    public Quaternion normalizeOrIdentity() {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return this;
        return new Quaternion(w / s, x / s, y / s, z / s);
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
    public Quaternion normalizeOrDefault(@Nonnull Quaternion q) {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return q;
        return new Quaternion(w / s, x / s, y / s, z / s);
    }
}
