package civitas.celestis.math;

import civitas.celestis.util.SafeArray;
import civitas.celestis.util.Tuple;
import civitas.celestis.util.io.ArrayReader;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An immutable four-dimensional vector. Numeric values are represented using
 * the primitive type {@code double}. Components are represented in WXYZ notation.
 *
 * @see Vector
 */
public class Vector4 implements Vector<Vector4> {
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
     *
     * @param w The W component of this vector
     * @param x The X component of this vector
     * @param y The Y component of this vector
     * @param z The Z component of this vector
     */
    public Vector4(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in WXYZ order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Vector4(@Nonnull double[] components) {
        if (components.length != 4) {
            throw new IllegalArgumentException("The provided array does not have a length of 4.");
        }

        this.w = components[0];
        this.x = components[1];
        this.y = components[2];
        this.z = components[3];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the provided vector {@code v} does not have four components
     */
    public Vector4(@Nonnull Vector<?> v) {
        if (v.dimensions() != 4) {
            throw new IllegalArgumentException("The provided vector is not four-dimensional.");
        }

        this.w = v.get(0);
        this.x = v.get(1);
        this.y = v.get(2);
        this.z = v.get(3);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Vector4(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.w = t.get(0).doubleValue();
        this.x = t.get(1).doubleValue();
        this.y = t.get(2).doubleValue();
        this.z = t.get(3).doubleValue();
    }

    /**
     * Creates a new vector. The required format is "{@code [0.0, 0.0, 0.0, 0.0]}".
     *
     * @param values The string representation of this vector
     * @throws NumberFormatException When the format is invalid
     */
    public Vector4(@Nonnull String values) {
        this(ArrayReader.readDoubleArray(values));
    }

    //
    // Variables
    //

    /**
     * The W component of this vector.
     */
    protected final double w;

    /**
     * The X component of this vector.
     */
    protected final double x;

    /**
     * The Y component of this vector.
     */
    protected final double y;

    /**
     * The Z component of this vector.
     */
    protected final double z;

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
        return 4;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return w == 0 && x == 0 && y == 0 && z == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(w + x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(w + x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(w + x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        return w * w + x * x + y * y + z * z;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        return Math.abs(w) + Math.abs(x) + Math.abs(y) + Math.abs(z);
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
            case 0 -> w;
            case 1 -> x;
            case 2 -> y;
            case 3 -> z;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 4.");
        };
    }

    /**
     * Returns the W component of this vector.
     *
     * @return The WX component of this vector
     */
    public double w() {
        return w;
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

    /**
     * Returns the Z component of this vector.
     *
     * @return The Z component of this vector
     */
    public double z() {
        return z;
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
    public Vector4 add(double s) {
        return new Vector4(w + s, x + s, y + s, z + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 subtract(double s) {
        return new Vector4(w - s, x - s, y - s, z - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 multiply(double s) {
        return new Vector4(w * s, x * s, y * s, z * s);
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
    public Vector4 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector4(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
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
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 subtract(@Nonnull Vector4 v) {
        return new Vector4(w - v.w, x - v.x, y - v.y, z - v.z);
    }

    /**
     * Multiplies this vector by another vector using quaternion multiplication.
     *
     * @param v The vector of which to multiply this vector by
     * @return The quaternion left-product of the two vectors
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
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull Vector4 v) {
        return w * v.w + x * v.x + y * v.y + z * v.z;
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
    public double distance(@Nonnull Vector4 v) {
        final double dw = w - v.w;
        final double dx = x - v.x;
        final double dy = y - v.y;
        final double dz = z - v.z;

        return Math.sqrt(dw * dw + dx * dx + dy * dy + dz * dz);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
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
     *
     * @param v The vector of which to get the Manhattan distance to
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
    public Vector4 min(@Nonnull Vector4 v) {
        return new Vector4(Math.min(w, v.w), Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 max(@Nonnull Vector4 v) {
        return new Vector4(Math.max(w, v.w), Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
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
    public Vector4 clamp(@Nonnull Vector4 min, @Nonnull Vector4 max) {
        return new Vector4(
                Numbers.clamp(w, min.w, max.w),
                Numbers.clamp(x, min.x, max.x),
                Numbers.clamp(y, min.y, max.y),
                Numbers.clamp(z, min.z, max.z)
        );
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
    public Vector4 negate() {
        return new Vector4(-w, -x, -y, -z);
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
    public Vector4 normalize() throws ArithmeticException {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector4(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 normalizeOrZero() {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return this;
        return new Vector4(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 normalizeOrDefault(@Nonnull Vector4 v) {
        final double s = Math.sqrt(w * w + x * x + y * y + z * z);
        if (s == 0) return v;
        return new Vector4(w / s, x / s, y / s, z / s);
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
    public Vector4 map(@Nonnull UnaryOperator<Double> f) {
        return new Vector4(f.apply(w), f.apply(x), f.apply(y), f.apply(z));
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
        return Tuple.of(f.apply(w), f.apply(x), f.apply(y), f.apply(z));
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
    public Vector4 merge(@Nonnull Vector4 v, @Nonnull BinaryOperator<Double> f) {
        return new Vector4(f.apply(w, v.w), f.apply(x, v.x), f.apply(y, v.y), f.apply(z, v.z));
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
        return SafeArray.ofDouble(w, x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> tuple() {
        return Tuple.of(w, x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(w, x, y, z);
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
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector<?> v)) return false;
        if (v.dimensions() != 4) return false;
        return w == v.get(0) && x == v.get(1) && y == v.get(2) && z == v.get(3);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Vector4 v) {
        return v != null && w == v.w && x == v.x && y == v.y && z == v.z;
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
        return "[" + w + ", " + x + ", " + y + ", " + z + "]";
    }
}
