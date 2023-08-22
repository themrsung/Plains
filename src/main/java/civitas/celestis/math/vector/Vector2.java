package civitas.celestis.math.vector;

import civitas.celestis.math.Scalars;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An immutable two-dimensional vector. Numeric values are represented using
 * the primitive type {@code double}. Components are represented in XY notation.
 *
 * @see Vector
 */
public class Vector2 implements Vector<Vector2> {
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
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in XY order
     * @throws IllegalArgumentException When the arrays' length is not {@code 2}
     */
    public Vector2(@Nonnull double[] components) {
        if (components.length != 2) {
            throw new IllegalArgumentException("The provided array does not have a length of 2.");
        }

        this.x = components[0];
        this.y = components[1];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the provided vector {@code v} does not have two components
     */
    public Vector2(@Nonnull Vector<?> v) {
        if (v.dimensions() != 2) {
            throw new IllegalArgumentException("The provided vector is not two-dimensional.");
        }

        this.x = v.get(0);
        this.y = v.get(1);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Vector2(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The provided tuple's size is not 2.");
        }

        this.x = t.get(0).doubleValue();
        this.y = t.get(1).doubleValue();
    }

    /**
     * Creates a new vector.
     *
     * @param a The array of which to copy component values from
     * @throws IllegalArgumentException When the provided array {@code a}'s length is not {@code 2}
     */
    public Vector2(@Nonnull SafeArray<? extends Number> a) {
        if (a.length() != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = a.get(0).doubleValue();
        this.y = a.get(1).doubleValue();
    }

    //
    // Variables
    //

    /**
     * The X component of this vector.
     */
    protected final double x;

    /**
     * The Y component of this vector.
     */
    protected final double y;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int dimensions() {
        return 2;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return x == 0 && y == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(x + y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(x + y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(x + y);
    }

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
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the component to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public double get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> x;
            case 1 -> y;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 2.");
        };
    }

    /**
     * Returns the X component of this vector.
     *
     * @return The X component of this vector
     */
    public double x() {
        return x;
    }

    /**
     * Returns the Y component of this vector.
     *
     * @return The Y component of this vector
     */
    public double y() {
        return y;
    }

    //
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to add to this vector
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
     * @param s The scalar to subtract this vector by
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
     * @param s The scalar to multiply this vector by
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
     * @param s The scalar to divide this vector by
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
     * Multiplies this vector by another vector using complex number multiplication.
     *
     * @param v The vector of which to multiply this vector by
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
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Euclidean distance to
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
     * @param v The vector of which to get the squared Euclidean distance to
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
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull Vector2 v) {
        final double dx = x - v.x;
        final double dy = y - v.y;

        return Math.abs(dx) + Math.abs(dy);
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
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
     * @param v The boundary vector to compare to
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
        double s = Math.sqrt(x * x + y * y);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector2(x / s, y / s);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 normalizeOrZero() {
        double s = Math.sqrt(x * x + y * y);
        if (s == 0) return this;
        return new Vector2(x / s, y / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 normalizeOrDefault(@Nonnull Vector2 v) {
        double s = Math.sqrt(x * x + y * y);
        if (s == 0) return v;
        return new Vector2(x / s, y / s);
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector counter-clockwise by the provided angle {@code t}.
     * Angle is denoted in radians.
     *
     * @param t The angle of rotation to apply in radians
     * @return The rotated vector
     * @see Math#toRadians(double)
     */
    @Nonnull
    public Vector2 rotate(double t) {
        final double c = Math.cos(t);
        final double s = Math.sin(t);

        return new Vector2(x * c - y * s, x * s + y * c);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 map(@Nonnull UnaryOperator<Double> f) {
        return new Vector2(f.apply(x), f.apply(y));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this vector
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> Tuple<F> mapToTuple(@Nonnull Function<Double, ? extends F> f) {
        return Tuple.of(f.apply(x), f.apply(y));
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
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public double[] array() {
        return new double[]{x, y};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Double> safeArray() {
        return SafeArray.ofDouble(x, y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(x, y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> tuple() {
        return Tuple.ofDouble(x, y);
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
        if (!(obj instanceof Vector<?> v)) return false;
        if (v.dimensions() != 2) return false;
        return x == v.get(0) && y == v.get(1);
    }

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

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
