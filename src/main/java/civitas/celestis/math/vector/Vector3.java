package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.io.ArrayReader;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An immutable three-dimensional vector. Numeric values are represented using
 * the primitive type {@code double}. Components are represented in XYZ notation.
 *
 * @see Vector
 */
public class Vector3 implements Vector<Vector3> {
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
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new vector.
     *
     * @param components An array containing the components of this vector in XYZ order
     * @throws IllegalArgumentException When the array's length is not {@code 3}
     */
    public Vector3(@Nonnull double[] components) {
        if (components.length != 3) {
            throw new IllegalArgumentException("The provided array does not have a length of 3.");
        }

        this.x = components[0];
        this.y = components[1];
        this.z = components[2];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the provided vector {@code v} does not have three components
     */
    public Vector3(@Nonnull Vector<?> v) {
        if (v.dimensions() != 3) {
            throw new IllegalArgumentException("The provided vector is not three-dimensional.");
        }

        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Vector3(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The provided tuple's size is not 3.");
        }

        this.x = t.get(0).doubleValue();
        this.y = t.get(1).doubleValue();
        this.z = t.get(2).doubleValue();
    }

    /**
     * Creates a new vector. The required format is "{@code [0.0, 0.0, 0.0]}".
     *
     * @param values The string representation of this vector
     * @throws NumberFormatException When the format is invalid
     */
    public Vector3(@Nonnull String values) {
        this(ArrayReader.readDoubleArray(values));
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
        return 3;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(x + y + z);
    }

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
            case 2 -> z;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 3.");
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
    public Vector3 add(double s) {
        return new Vector3(x + s, y + s, z + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract this vector by
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
     * @param s The scalar to multiply this vector by
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
     * @param s The scalar to divide this vector by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
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
     * Between this vector and the provided vector {@code v}, this returns the
     * cross product of the two vectors.
     *
     * @param v The vector of which to get the cross product between
     * @return The cross product of the two vectors
     */
    @Nonnull
    public Vector3 cross(@Nonnull Vector3 v) {
        return new Vector3(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
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
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Euclidean distance to
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
     * @param v The vector of which to get the squared Euclidean distance to
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
     * @param v The vector of which to get the Manhattan distance to
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
    public Vector3 min(@Nonnull Vector3 v) {
        return new Vector3(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
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
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
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
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Vector3(x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 normalizeOrZero() {
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) return this;
        return new Vector3(x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 normalizeOrDefault(@Nonnull Vector3 v) {
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) return v;
        return new Vector3(x / s, y / s, z / s);
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector by the provided rotation quaternion {@code q}, then returns
     * the rotated vector.
     *
     * @param q The rotation quaternion representing the rotation to apply to this vector
     * @return The resulting vector
     */
    @Nonnull
    public Vector3 rotate(@Nonnull Quaternion q) {
        return q.multiply(quaternion()).multiply(q.conjugate()).vector();
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
    public Vector3 map(@Nonnull UnaryOperator<Double> f) {
        return new Vector3(f.apply(x), f.apply(y), f.apply(z));
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
        return Tuple.of(f.apply(x), f.apply(y), f.apply(z));
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
        return SafeArray.ofDouble(x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> tuple() {
        return Tuple.ofDouble(x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(x, y, z);
    }

    /**
     * Returns a pure quaternion whose vector part (the XYZ components) is populated
     * with this vector's component values. In other words, converts this vector into
     * a pure quaternion. (a quaternion with the scalar part being zero)
     *
     * @return A pure quaternion whose vector part is populated by the components of this vector
     */
    @Nonnull
    public Quaternion quaternion() {
        return new Quaternion(0, x, y, z);
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
        if (v.dimensions() != 3) return false;
        return x == v.get(0) && y == v.get(1) && z == v.get(2);
    }

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
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
