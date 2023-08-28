package civitas.celestis.math.vector;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.util.tuple.Double3;
import civitas.celestis.util.tuple.DoubleTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * An immutable three-dimensional vector which uses the type {@code double}.
 *
 * @see Double3
 * @see Vector
 */
public class Vector3 extends Double3 implements Vector<Vector3> {
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
    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    /**
     * The positive X unit vector.
     */
    public static final Vector3 POSITIVE_X = new Vector3(1, 0, 0);

    /**
     * The positive Y unit vector.
     */
    public static final Vector3 POSITIVE_Y = new Vector3(0, 1, 0);

    /**
     * The positive Z unit vector.
     */
    public static final Vector3 POSITIVE_Z = new Vector3(0, 0, 1);

    /**
     * The negative X unit vector.
     */
    public static final Vector3 NEGATIVE_X = new Vector3(-1, 0, 0);

    /**
     * The negative Y unit vector.
     */
    public static final Vector3 NEGATIVE_Y = new Vector3(0, -1, 0);

    /**
     * The negative Z unit vector.
     */
    public static final Vector3 NEGATIVE_Z = new Vector3(0, 0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param x The X component of this vector
     * @param y The Y component of this vector
     * @param z The Z component of this vector
     */
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in XYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Vector3(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Vector3(@Nonnull DoubleTuple t) {
        super(t);
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
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        return x * x + y * y + z * z;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
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
    public Vector3 add(double s) {
        return new Vector3(x + s, y + s, z + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 subtract(double s) {
        return new Vector3(x - s, y - s, z - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 multiply(double s) {
        return new Vector3(x * s, y * s, z * s);
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
    public Vector3 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector3(x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 add(@Nonnull Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 subtract(@Nonnull Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the cross product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the cross product between
     * @return The cross product between the two vectors
     */
    @Nonnull
    public Vector3 cross(@Nonnull Vector3 v) {
        return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
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
    public Vector3 normalize() throws ArithmeticException {
        final double n = Math.sqrt(x * x + y * y + z * z);
        if (n == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector3(x / n, y / n, z / n);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 normalizeOrZero() {
        final double n = Math.sqrt(x * x + y * y + z * z);
        if (n == 0) return this;
        return new Vector3(x / n, y / n, z / n);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to return in case of division by zero
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 normalizeOrDefault(@Nonnull Vector3 v) {
        final double n = Math.sqrt(x * x + y * y + z * z);
        if (n == 0) return v;
        return new Vector3(x / n, y / n, z / n);
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
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
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
    public Vector3 min(@Nonnull Vector3 v) {
        return new Vector3(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 max(@Nonnull Vector3 v) {
        return new Vector3(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
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
    public Vector3 clamp(@Nonnull Vector3 min, @Nonnull Vector3 max) {
        return new Vector3(
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
     *
     * @param v The vector of which get the Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance(@Nonnull Vector3 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance2(@Nonnull Vector3 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull Vector3 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 map(@Nonnull DoubleUnaryOperator f) {
        return new Vector3(f.applyAsDouble(x), f.applyAsDouble(y), f.applyAsDouble(z));
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
    public Vector3 merge(@Nonnull Vector3 v, @Nonnull BinaryOperator<Double> f) {
        return new Vector3(f.apply(x, v.x), f.apply(y, v.y), f.apply(z, v.z));
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
    public Quaternion quaternion() {
        return new Quaternion(0, x, y, z);
    }

    /**
     * Rotates this vector by the provided rotation quaternion {@code q}.
     *
     * @param q The rotation quaternion of which to apply to this vector
     * @return The rotated vector
     */
    @Nonnull
    public Vector3 rotate(@Nonnull Quaternion q) {
        return q.multiply(quaternion()).multiply(q.conjugate()).vector();
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
    public boolean equals(@Nullable Vector3 v) {
        return v != null && x == v.x && y == v.y && z == v.z;
    }
}
