package civitas.celestis.math.vector;

import civitas.celestis.math.Scalars;
import civitas.celestis.util.tuple.Double3;
import civitas.celestis.util.tuple.Double4;
import civitas.celestis.util.tuple.DoubleTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * An immutable three-dimensional vector which uses the type {@code double}.
 * @see Double3
 * @see Vector
 */
public class Vector4 extends Double4 implements Vector<Vector4> {
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
    public static final Vector4 ZERO = new Vector4(0, 0, 0, 0);

    /**
     * The positive W unit vector.
     */
    public static final Vector4 POSITIVE_W = new Vector4(1, 0, 0, 0);

    /**
     * The positive X unit vector.
     */
    public static final Vector4 POSITIVE_X = new Vector4(0, 1, 0, 0);

    /**
     * The positive Y unit vector.
     */
    public static final Vector4 POSITIVE_Y = new Vector4(0, 0, 1, 0);

    /**
     * The positive Z unit vector.
     */
    public static final Vector4 POSITIVE_Z = new Vector4(0, 0, 0, 1);

    /**
     * The negative W unit vector.
     */
    public static final Vector4 NEGATIVE_W = new Vector4(-1, 0, 0, 0);

    /**
     * The negative X unit vector.
     */
    public static final Vector4 NEGATIVE_X = new Vector4(0, -1, 0, 0);

    /**
     * The negative Y unit vector.
     */
    public static final Vector4 NEGATIVE_Y = new Vector4(0, 0, -1, 0);

    /**
     * The negative Z unit vector.
     */
    public static final Vector4 NEGATIVE_Z = new Vector4(0, 0, 0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     * @param w The W component of this vector
     * @param x The X component of this vector
     * @param y The Y component of this vector
     * @param z The Z component of this vector
     */
    public Vector4(double w, double x, double y, double z) {
        super(w, x, y, z);
    }

    /**
     * Creates a new vector.
     * @param components An array containing the components of this vector in WXYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public Vector4(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new vector.
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Vector4(@Nonnull DoubleTuple t) {
        super(t);
    }

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        return w * w + x * x + y * y + z * z;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        return Math.abs(w) + Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    //
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     * @param s The scalar of which to add to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 add(double s) {
        return new Vector4(w + s, x + s, y + s, z + s);
    }

    /**
     * {@inheritDoc}
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 subtract(double s) {
        return new Vector4(w - s, x - s, y - s, z - s);
    }

    /**
     * {@inheritDoc}
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 multiply(double s) {
        return new Vector4(w * s, x * s, y * s, z * s);
    }

    /**
     * {@inheritDoc}
     * @param s The scalar of which to divide each component of this vector by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector4(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 add(@Nonnull Vector4 v) {
        return new Vector4(w + v.w, x + v.x, y + v.y, z + v.z);
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 subtract(@Nonnull Vector4 v) {
        return new Vector4(w - v.w, x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the quaternion left-product between this vector and the provided vector {@code v}.
     * @param v The vector of which to get the quaternion left-product between
     * @return The quaternion left-product between the two vectors
     */
    @Nonnull
    public Vector4 multiply(@Nonnull Vector4 v) {
        return new Vector4(
                w * v.w - x * v.x - y * v.y - z * v.z,
                w * v.x + x * v.w + y * v.z - z * v.y,
                w * v.y - x * v.z + y * v.w + z * v.x,
                w * v.z + x * v.y - y * v.x + z * v.w
        );
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull Vector4 v) {
        return w * v.w + x * v.x + y * v.y + z * v.z;
    }

    //
    // Normalization
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 normalize() throws ArithmeticException {
        final double n = Math.sqrt(w * w + x * x + y * y + z * z);
        if (n == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector4(w / n, x / n, y / n, z / n);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 normalizeOrZero() {
        final double n = Math.sqrt(w * w + x * x + y * y + z * z);
        if (n == 0) return this;
        return new Vector4(w / n, x / n, y / n, z / n);
    }

    /**
     * {@inheritDoc}
     * @param v The value of which to return in case of division by zero
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 normalizeOrDefault(@Nonnull Vector4 v) {
        final double n = Math.sqrt(w * w + x * x + y * y + z * z);
        if (n == 0) return v;
        return new Vector4(w / n, x / n, y / n, z / n);
    }

    //
    // Negation
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 negate() {
        return new Vector4(-w, -x, -y, -z);
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 min(@Nonnull Vector4 v) {
        return new Vector4(Math.min(w, v.w), Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
    }

    /**
     * {@inheritDoc}
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 max(@Nonnull Vector4 v) {
        return new Vector4(Math.max(w, v.w), Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
    }

    /**
     * {@inheritDoc}
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 clamp(@Nonnull Vector4 min, @Nonnull Vector4 max) {
        return new Vector4(
                Scalars.clamp(w, min.w, max.w),
                Scalars.clamp(x, min.x, max.x),
                Scalars.clamp(y, min.y, max.y),
                Scalars.clamp(z, min.z, max.z)
        );
    }

    //
    // Distance
    //

    /**
     * {@inheritDoc}
     * @param v The vector of which get the Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance(@Nonnull Vector4 v) {
        final double dw = w - v.w;
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return Math.sqrt(dw * dw + dx * dx + dy * dy + dz * dz);
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance2(@Nonnull Vector4 v) {
        final double dw = w - v.w;
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return dw * dw + dx * dx + dy * dy + dz * dz;
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull Vector4 v) {
        final double dw = w - v.w;
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return Math.abs(dw) + Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     * @param f The function of which to apply to each component of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 map(@Nonnull DoubleUnaryOperator f) {
        return new Vector4(f.applyAsDouble(w), f.applyAsDouble(x), f.applyAsDouble(y), f.applyAsDouble(z));
    }

    /**
     * {@inheritDoc}
     * @param v The vector of which to merge this vector with
     * @param f The merger function to handle the merging of the two vectors
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 merge(@Nonnull Vector4 v, @Nonnull BinaryOperator<Double> f) {
        return new Vector4(f.apply(w, v.w), f.apply(x, v.x), f.apply(y, v.y), f.apply(z, v.z));
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Vector4 v) {
        return v != null && w == v.w && x == v.x && y == v.y && z == v.z;
    }
}
