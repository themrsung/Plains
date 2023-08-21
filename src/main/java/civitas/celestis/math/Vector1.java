package civitas.celestis.math;

import civitas.celestis.util.SafeArray;
import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An immutable one-dimensional vector. Numeric values are represented using
 * the primitive type {@code double}. Components are represented in X notation.
 *
 * @see Vector
 */
public class Vector1 implements Vector<Vector1> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * A vector with no direction or magnitude. Represents origin.
     */
    public static final Vector1 ZERO = new Vector1(0);

    /**
     * The positive X unit vector.
     */
    public static final Vector1 POSITIVE_X = new Vector1(1);

    /**
     * The negative X unit vector.
     */
    public static final Vector1 NEGATIVE_X = new Vector1(-1);

    /**
     * Creates a new vector.
     *
     * @param x The X (and only) component of this vector
     */
    public Vector1(double x) {
        this.x = x;
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the provided vector {@code v} does not have one component
     */
    public Vector1(@Nonnull Vector<?> v) {
        if (v.dimensions() != 1) {
            throw new IllegalArgumentException("The provided vector is not one-dimensional.");
        }

        this.x = v.get(0);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 1}
     */
    public Vector1(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 1) {
            throw new IllegalArgumentException("The provided tuple's size is not 1.");
        }

        this.x = t.get(0).doubleValue();
    }

    /**
     * Creates a new vector. The required format is "{@code [0.0]}".
     *
     * @param values The string representation of this vector
     * @throws NumberFormatException When the format is invalid
     */
    public Vector1(@Nonnull String values) {
        this.x = Double.parseDouble(values.replaceAll("[\\[]]", ""));
    }

    //
    // Variables
    //

    /**
     * The X (and only) component of this vector.
     */
    protected final double x;

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
        return 1;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return x == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.abs(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        return x * x;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        return Math.abs(x);
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
        if (i != 0) throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 1.");
        return x;
    }

    /**
     * Returns the X (and only) component of this vector.
     *
     * @return The X component of this vector
     */
    public double x() {
        return x;
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
    public Vector1 add(double s) {
        return new Vector1(x + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 subtract(double s) {
        return new Vector1(x - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 multiply(double s) {
        return new Vector1(x * s);
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
    public Vector1 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector1(x / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 add(@Nonnull Vector1 v) {
        return new Vector1(x + v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 subtract(@Nonnull Vector1 v) {
        return new Vector1(x - v.x);
    }

    /**
     * Multiplies this vector by another vector using scalar multiplication.
     *
     * @param v The vector of which to multiply this vector by
     * @return The scalar product of the two vectors
     */
    @Nonnull
    public Vector1 multiply(@Nonnull Vector1 v) {
        return new Vector1(x * v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull Vector1 v) {
        return x * v.x;
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
    public double distance(@Nonnull Vector1 v) {
        return Math.abs(x - v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public double distance2(@Nonnull Vector1 v) {
        final double dx = x - v.x;
        return dx * dx;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull Vector1 v) {
        return Math.abs(x - v.x);
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
    public Vector1 min(@Nonnull Vector1 v) {
        return new Vector1(Math.min(x, v.x));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 max(@Nonnull Vector1 v) {
        return new Vector1(Math.max(x, v.x));
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
    public Vector1 clamp(@Nonnull Vector1 min, @Nonnull Vector1 max) {
        return new Vector1(Numbers.clamp(x, min.x, max.x));
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
    public Vector1 negate() {
        return new Vector1(-x);
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
    public Vector1 normalize() throws ArithmeticException {
        if (x == 0) throw new ArithmeticException("Cannot divide by zero.");
        return x > 0 ? POSITIVE_X : NEGATIVE_X;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 normalizeOrZero() {
        if (x == 0) return ZERO;
        return x > 0 ? POSITIVE_X : NEGATIVE_X;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector1 normalizeOrDefault(@Nonnull Vector1 v) {
        if (x == 0) return v;
        return x > 0 ? POSITIVE_X : NEGATIVE_X;
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
    public Vector1 map(@Nonnull UnaryOperator<Double> f) {
        return new Vector1(f.apply(x));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this vector
     * @param <T> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <T> Tuple<T> mapToTuple(@Nonnull Function<Double, T> f) {
        return Tuple.of(f.apply(x));
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
    public Vector1 merge(@Nonnull Vector1 v, @Nonnull BinaryOperator<Double> f) {
        return new Vector1(f.apply(x, v.x));
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
    public SafeArray<Double> array() {
        return SafeArray.ofDouble(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> tuple() {
        return Tuple.ofDouble(x);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(x);
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
        if (v.dimensions() != 1) return false;
        return x == v.get(0);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Vector1 v) {
        return v != null && x == v.x;
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "[" + x + "]";
    }
}
