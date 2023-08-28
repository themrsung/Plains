package civitas.celestis.math.vector;

import civitas.celestis.math.Scalars;
import civitas.celestis.util.tuple.Double2;
import civitas.celestis.util.tuple.DoubleTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * An immutable two-dimensional vector which uses the type {@code double}.
 *
 * @see Double2
 * @see Vector
 */
public class Vector2 extends Double2 implements Vector<Vector2> {
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
    public static final Vector2 ZERO = new Vector2(0, 0);

    /**
     * The positive X unit vector.
     */
    public static final Vector2 POSITIVE_X = new Vector2(1, 0);

    /**
     * The positive Y unit vector.
     */
    public static final Vector2 POSITIVE_Y = new Vector2(0, 1);

    /**
     * The negative X unit vector.
     */
    public static final Vector2 NEGATIVE_X = new Vector2(-1, 0);

    /**
     * The negative Y unit vector.
     */
    public static final Vector2 NEGATIVE_Y = new Vector2(0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param x The X component of this vector
     * @param y The Y component of this vector
     */
    public Vector2(double x, double y) {
        super(x, y);
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in XY order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Vector2(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Vector2(@Nonnull DoubleTuple t) {
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
        return Math.sqrt(x * x + y * y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        return x * x + y * y;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        return Math.abs(x) + Math.abs(y);
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
    public Vector2 add(double s) {
        return new Vector2(x + s, y + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 subtract(double s) {
        return new Vector2(x - s, y - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 multiply(double s) {
        return new Vector2(x * s, y * s);
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
    public Vector2 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector2(x / s, y / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 add(@Nonnull Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 subtract(@Nonnull Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    /**
     * Returns the complex number product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the complex number product between
     * @return The complex number product between the two vectors
     */
    @Nonnull
    public Vector2 multiply(@Nonnull Vector2 v) {
        return new Vector2(x * v.x - y * v.y, x * v.y + y * v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull Vector2 v) {
        return x * v.x + y * v.y;
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
    public Vector2 normalize() throws ArithmeticException {
        final double n = Math.sqrt(x * x + y * y);
        if (n == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector2(x / n, y / n);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 normalizeOrZero() {
        final double n = Math.sqrt(x * x + y * y);
        if (n == 0) return this;
        return new Vector2(x / n, y / n);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to return in case of division by zero
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 normalizeOrDefault(@Nonnull Vector2 v) {
        final double n = Math.sqrt(x * x + y * y);
        if (n == 0) return v;
        return new Vector2(x / n, y / n);
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
    public Vector2 negate() {
        return new Vector2(-x, -y);
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
    public Vector2 min(@Nonnull Vector2 v) {
        return new Vector2(Math.min(x, v.x), Math.min(y, v.y));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 max(@Nonnull Vector2 v) {
        return new Vector2(Math.max(x, v.x), Math.max(y, v.y));
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
    public Vector2 clamp(@Nonnull Vector2 min, @Nonnull Vector2 max) {
        return new Vector2(Scalars.clamp(x, min.x, max.x), Scalars.clamp(y, min.y, max.y));
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
    public double distance(@Nonnull Vector2 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance2(@Nonnull Vector2 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;

        return dx * dx + dy * dy;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull Vector2 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;

        return Math.abs(dx) + Math.abs(dy);
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
    public Vector2 map(@Nonnull DoubleUnaryOperator f) {
        return new Vector2(f.applyAsDouble(x), f.applyAsDouble(y));
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
    public Vector2 merge(@Nonnull Vector2 v, @Nonnull BinaryOperator<Double> f) {
        return new Vector2(f.apply(x, v.x), f.apply(y, v.y));
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
    public boolean equals(@Nullable Vector2 v) {
        return v != null && x == v.x && y == v.y;
    }
}
