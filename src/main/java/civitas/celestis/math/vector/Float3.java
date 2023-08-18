package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import civitas.celestis.util.group.Triple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * A three-dimensional {@code float} vector which uses XYZ notation.
 * <p>
 * Note that while using 32-bit single precision types may conserve memory,
 * many of the core vector operations such as normalization are still
 * computed by active conversion to and from 64-bit {@code double}s.
 * </p>
 */
public class Float3 implements FloatVector<Float3> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -2326721508603861284L;

    /**
     * A vector with no direction or magnitude. Represents origin.
     */
    public static final Float3 ZERO = new Float3(0, 0, 0);

    /**
     * The positive X unit vector.
     */
    public static final Float3 POSITIVE_X = new Float3(1, 0, 0);

    /**
     * The positive Y unit vector.
     */
    public static final Float3 POSITIVE_Y = new Float3(0, 1, 0);

    /**
     * Ths positive Z unit vector.
     */
    public static final Float3 POSITIVE_Z = new Float3(0, 0, 1);

    /**
     * The negative X unit vector.
     */
    public static final Float3 NEGATIVE_X = new Float3(-1, 0, 0);

    /**
     * The negative Y unit vector.
     */
    public static final Float3 NEGATIVE_Y = new Float3(0, -1, 0);

    /**
     * The negative Z unit vector.
     */
    public static final Float3 NEGATIVE_Z = new Float3(0, 0, -1);

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
    public Float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new vector.
     *
     * @param values An array containing the values of this vector in XYZ order
     */
    public Float3(@Nonnull float[] values) {
        if (values.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Float3(@Nonnull Vector<?, ?> v) {
        final Float[] values = v.list().stream().map(Number::floatValue).toArray(Float[]::new);
        if (values.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Float3(@Nonnull FloatVector<?> v) {
        this(v.array());
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Float3(@Nonnull Float3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Float3(@Nonnull Vector3 v) {
        this.x = (float) v.x;
        this.y = (float) v.y;
        this.z = (float) v.z;
    }

    //
    // Variables
    //

    /**
     * The X component of this vector.
     */
    protected final float x;

    /**
     * The Y component of this vector.
     */
    protected final float y;

    /**
     * The Z component of this vector.
     */
    protected final float z;

    //
    // Properties
    //

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
        return Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Float.isFinite(x) && Float.isFinite(y) && Float.isFinite(z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Float.isInfinite(x) || Float.isInfinite(y) || Float.isInfinite(z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float norm() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float norm2() {
        return x * x + y * y + z * z;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float normManhattan() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    //
    // Getters
    //

    /**
     * Returns the X component of this vector.
     *
     * @return The X component of this vector
     */
    public final float x() {
        return x;
    }

    /**
     * Returns the Y component of this vector.
     *
     * @return The Y component of this vector
     */
    public final float y() {
        return y;
    }

    /**
     * Returns the Z component of this vector.
     *
     * @return The Z component of this vector
     */
    public final float z() {
        return z;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public final float[] array() {
        return new float[]{x, y, z};
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
    public Float3 add(float s) {
        return new Float3(x + s, y + s, z + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 subtract(float s) {
        return new Float3(x - s, y - s, z - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 multiply(float s) {
        return new Float3(x * s, y * s, z * s);
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
    public Float3 divide(float s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Float3(x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 divideAllowZero(float s) {
        return new Float3(x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 add(@Nonnull Float3 v) {
        return new Float3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 subtract(@Nonnull Float3 v) {
        return new Float3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the cross product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the cross product between
     * @return The resulting vector
     */
    @Nonnull
    public Float3 cross(@Nonnull Float3 v) {
        return new Float3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public float dot(@Nonnull Float3 v) {
        return x * v.x + y * v.y + z * v.z;
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
    public Float3 negate() {
        return new Float3(-x, -y, -z);
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
    public Float3 normalize() throws ArithmeticException {
        final float n = (float) Math.sqrt(x * x + y * y + z * z);
        if (n == 0) throw new ArithmeticException("Cannot normalize a vector with zero magnitude.");
        return new Float3(x / n, y / n, z / n);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 normalizeOrZero() {
        if (x == 0 && y == 0 && z == 0) return this; // This is the only possible vector with zero magnitude
        final float n = (float) Math.sqrt(x * x + y * y + z * z);
        return new Float3(x / n, y / n, z / n);
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
    public float distance(@Nonnull Float3 v) {
        final float dx = x - v.x;
        final float dy = y - v.y;
        final float dz = z - v.z;

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public float distance2(@Nonnull Float3 v) {
        final float dx = x - v.x;
        final float dy = y - v.y;
        final float dz = z - v.z;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public float distanceManhattan(@Nonnull Float3 v) {
        final float dx = x - v.x;
        final float dy = y - v.y;
        final float dz = z - v.z;

        return Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to respect
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 min(@Nonnull Float3 v) {
        return new Float3(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.y));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to respect
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 max(@Nonnull Float3 v) {
        return new Float3(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.y));
    }

    /**
     * {@inheritDoc}
     *
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 clamp(@Nonnull Float3 min, @Nonnull Float3 max) {
        return new Float3(
                (float) Numbers.clamp(x, min.x, max.x),
                (float) Numbers.clamp(y, min.y, max.y),
                (float) Numbers.clamp(z, min.z, max.z)
        );
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Float3 transform(@Nonnull Function<? super Float, Float> f) {
        return new Float3(f.apply(x), f.apply(y), f.apply(z));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function to apply to each element of this vector
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Triple<F> map(@Nonnull Function<? super Float, F> f) {
        return new Triple<>(f.apply(x), f.apply(y), f.apply(z));
    }

    //
    // Rotation
    //

    /**
     * Converts this vector into a pure quaternion.
     * A pure quaternion is a quaternion where the scalar part (the W component)
     * is zero, and the imaginary part (the XYZ components)
     * is populated by tangible values.
     *
     * @return The pure quaternion of this vector
     */
    @Nonnull
    public Quaternion quaternion() {
        return new Quaternion(0, x, y, z);
    }

    /**
     * Rotates this vector by a rotation quaternion.
     *
     * @param rq The rotation to apply to this vector in the form of a quaternion
     * @return The rotated vector
     */
    @Nonnull
    public Float3 rotate(@Nonnull Quaternion rq) {
        return new Float3(rq.multiply(quaternion()).multiply(rq.conjugate()).vector());
    }

    //
    // Type Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Float> list() {
        return List.of(x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Float> collect() {
        return List.of(x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Triple<Float> group() {
        return new Triple<>(x, y, z);
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
        if (obj instanceof Float3 v) {
            return x == v.x && y == v.y && z == v.z;
        }

        if (obj instanceof FloatVector<?> dv) {
            return Arrays.equals(array(), dv.array());
        }

        if (obj instanceof Vector<?, ?> v) {
            final float[] a1 = array();
            final Number[] a2 = v.collect().toArray(Number[]::new);

            if (a2.length != 3) return false;

            for (int i = 0; i < 3; i++) {
                if (a1[i] != a2[i].floatValue()) return false;
            }

            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Float3 v) {
        if (v == null) return false;
        return x == v.x && y == v.y && z == v.z;
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string into a vector.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Float3 parseVector(@Nonnull String input) throws NumberFormatException {
        if (!input.startsWith("Vector{")) {
            throw new NumberFormatException("The provided string is not a vector.");
        }

        final String[] valueStrings = input.replaceAll("Vector\\{|}", "").split(", ");
        if (valueStrings.length != 3) {
            throw new NumberFormatException("The provided string does not have three components.");
        }

        final float[] values = new float[3];

        for (final String valueString : valueStrings) {
            final String[] split = valueString.split("=");
            if (split.length != 2) {
                throw new NumberFormatException("The format is invalid.");
            }

            values[switch (split[0]) {
                case "x" -> 0;
                case "y" -> 1;
                case "z" -> 2;
                default -> throw new NumberFormatException("The provided string has a non-XYZ component.");
            }] = Float.parseFloat(split[1]);
        }

        return new Float3(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
