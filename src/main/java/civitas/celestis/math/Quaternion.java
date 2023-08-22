package civitas.celestis.math;

import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A complex number with three imaginary parts. Quaternions are often used to represent
 * the rotation of three-dimensional vectors without suffering from gimbal lock.
 * <p>
 * While arithmetic operations share some similarities with those of four-dimensional vectors,
 * quaternions have unique properties and operations that make them especially suitable
 * for representing rotations and orientations in three-dimensional space.
 * </p>
 *
 * @see Vector4
 */
public class Quaternion extends Vector4 {
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
     * @param s The scalar part (the W component) of this quaternion
     * @param v The vector part (the XYZ components) of this quaternion
     */
    public Quaternion(double s, @Nonnull Vector3 v) {
        super(s, v.x(), v.y(), v.z());
    }

    /**
     * Creates a new quaternion.
     *
     * @param components An array containing the components of this quaternion in WXYZ order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Quaternion(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new quaternion.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the vector is not four-dimensional
     */
    public Quaternion(@Nonnull Vector<?> v) {
        super(v);
    }

    /**
     * Creates a new quaternion.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the tuple's size is not {@code 4}
     */
    public Quaternion(@Nonnull Tuple<? extends Number> t) {
        super(t);
    }

    /**
     * Creates a new quaternion. The required format is "{@code [0.0, 0.0, 0.0, 0.0]}".
     *
     * @param values The string representation of this quaternion
     * @throws NumberFormatException When the format is invalid
     */
    public Quaternion(@Nonnull String values) {
        super(values);
    }

    //
    // Properties
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
     * @throws ArithmeticException When the squared Euclidean norm of this quaternion is zero
     */
    @Nonnull
    public Quaternion inverse() throws ArithmeticException {
        final double n2 = norm2();
        if (n2 == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / n2, -x / n2, -y / n2, -z / n2);
    }

    //
    // Getters
    //

    /**
     * Returns the scalar part (the W component) of this quaternion.
     *
     * @return The scalar part of this quaternion
     */
    public double scalar() {
        return w;
    }

    /**
     * Returns the vector part (the XYZ components) of this quaternion.
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
     * Adds a scalar to this quaternion.
     *
     * @param s The scalar to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion add(double s) {
        return new Quaternion(w + s, x, y, z);
    }

    /**
     * Subtracts this quaternion by a scalar.
     *
     * @param s The scalar to subtract this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion subtract(double s) {
        return new Quaternion(w - s, x, y, z);
    }

    /**
     * Multiplies this quaternion by a scalar. This is not appropriate to use as a substitute for
     * {@link #scale(double)}, as it changes the magnitude of this quaternion, no longer making it
     * a rotation quaternion. (a quaternion with a Euclidean norm of {@code 1})
     *
     * @param s The scalar to multiply this quaternion by
     * @return The resulting quaternion
     * @see #scale(double)
     */
    @Nonnull
    @Override
    public Quaternion multiply(double s) {
        return new Quaternion(w * s, x * s, y * s, z * s);
    }

    /**
     * Scales the rotation of this quaternion.
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

        final double halfAngle = Math.acos(w());
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
     * Divides this quaternion by a scalar.
     *
     * @param s The scalar to divide this quaternion by
     * @return The resulting quaternion
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * Adds a vector to this quaternion. The vector is treated as if it were a quaternion.
     *
     * @param v The vector of which to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion add(@Nonnull Vector4 v) {
        return new Quaternion(w + v.w(), x + v.x(), y + v.y(), z + v.z());
    }

    /**
     * Subtracts this quaternion by a vector. The vector is treated as if it were a quaternion.
     *
     * @param v The vector of which to subtract from this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion subtract(@Nonnull Vector4 v) {
        return new Quaternion(w - v.w(), x - v.x(), y - v.y(), z - v.z());
    }

    /**
     * Multiplies this quaternion by another vector. Quaternion multiplication is used.
     *
     * @param v The vector of which to multiply this quaternion by
     * @return The quaternion left-product between this quaternion and the provided vector {@code v}
     */
    @Nonnull
    @Override
    public Quaternion multiply(@Nonnull Vector4 v) {
        final double vw = v.w();
        final double vx = v.x();
        final double vy = v.y();
        final double vz = v.z();

        return new Quaternion(
                w * vw - x * vx - y * vy - z * vz,
                w * vx + x * vw + y * vz - z * vy,
                w * vy - x * vz + y * vw + z * vx,
                w * vz + x * vy - y * vx + z * vw
        );
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
    public Quaternion negate() {
        return new Quaternion(-w, -x, -y, -z);
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
    public Quaternion normalize() throws ArithmeticException {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion normalizeOrZero() {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return this;
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion normalizeOrDefault(@Nonnull Vector4 v) {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return new Quaternion(v);
        return new Quaternion(w / s, x / s, y / s, z / s);
    }
}
