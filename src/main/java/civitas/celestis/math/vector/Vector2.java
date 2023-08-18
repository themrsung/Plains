package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import civitas.celestis.util.group.Pair;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * A two-dimensional {@code double} vector which uses XY notation.
 */
public class Vector2 implements DoubleVector<Vector2> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 5868889097902659292L;


    /**
     * A vector with no direction or magnitude. Represents origin.
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
     * @param values An array containing the values of this vector in XY order
     */
    public Vector2(@Nonnull double[] values) {
        if (values.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = values[0];
        this.y = values[1];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Vector2(@Nonnull Vector<?, ?> v) {
        final Double[] values = v.list().stream().map(Number::doubleValue).toArray(Double[]::new);
        if (values.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = values[0];
        this.y = values[1];
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Vector2(@Nonnull DoubleVector<?> v) {
        this(v.array());
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public Vector2(@Nonnull Vector2 v) {
        this.x = v.x;
        this.y = v.y;
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
        return Double.isNaN(x) || Double.isNaN(y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(x) && Double.isFinite(y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(x) || Double.isInfinite(y);
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
     * Returns the X component of this vector.
     *
     * @return The X component of this vector
     */
    public final double x() {
        return x;
    }

    /**
     * Returns the Y component of this vector.
     *
     * @return The Y component of this vector
     */
    public final double y() {
        return y;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public final double[] array() {
        return new double[]{x, y};
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
     * @param s The scalar to subtract from this vector
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
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Vector2(x / s, y / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 divideAllowZero(double s) {
        return new Vector2(x / s, y / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to add to this vector
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
     * @param v The vector to subtract from this vector
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
     * @param v The vector to multiply this vector by
     * @return The complex number product of the two vectors
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
        final double n = Math.sqrt(x * x + y * y);
        if (n == 0) throw new ArithmeticException("Cannot normalize a vector with zero magnitude.");
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
        if (x == 0 && y == 0) return this; // This is the only possible vector with zero magnitude
        final double n = Math.sqrt(x * x + y * y);
        return new Vector2(x / n, y / n);
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
     * @param v The boundary vector to respect
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
     * @param v The boundary vector to respect
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
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 clamp(@Nonnull Vector2 min, @Nonnull Vector2 max) {
        return new Vector2(
                Numbers.clamp(x, min.x, max.x),
                Numbers.clamp(y, min.y, max.y)
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
    public Vector2 transform(@Nonnull Function<? super Double, Double> f) {
        return new Vector2(f.apply(x), f.apply(y));
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
    public <F> Pair<F> map(@Nonnull Function<? super Double, F> f) {
        return new Pair<>(f.apply(x), f.apply(y));
    }

    //
    // Rotation
    //

    /**
     * Rotates this vector counter-clockwise by the provided angle.
     *
     * @param angRads The angle of rotation to apply to this vector in radians
     * @return The rotated vector
     */
    @Nonnull
    public Vector2 rotate(double angRads) {
        final double cos = Math.cos(angRads);
        final double sin = Math.sin(angRads);

        return new Vector2(
                cos * x - sin * y,
                sin * x + cos * y
        );
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
    public Collection<Double> collect() {
        return List.of(x, y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Pair<Double> group() {
        return new Pair<>(x, y);
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
        if (obj instanceof Vector2 v) {
            return x == v.x && y == v.y;
        }

        if (obj instanceof DoubleVector<?> dv) {
            return Arrays.equals(array(), dv.array());
        }

        if (obj instanceof Vector<?, ?> v) {
            final double[] a1 = array();
            final Number[] a2 = v.collect().toArray(Number[]::new);

            if (a2.length != 2) return false;

            for (int i = 0; i < 2; i++) {
                if (a1[i] != a2[i].doubleValue()) return false;
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
    public boolean equals(@Nullable Vector2 v) {
        if (v == null) return false;
        return x == v.x && y == v.y;
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
    public static Vector2 parseVector(@Nonnull String input) throws NumberFormatException {
        if (!input.startsWith("Vector{")) {
            throw new NumberFormatException("The provided string is not a vector.");
        }

        final String[] valueStrings = input.replaceAll("Vector\\{|}", "").split(", ");
        if (valueStrings.length != 3) {
            throw new NumberFormatException("The provided string does not have three components.");
        }

        final double[] values = new double[2];

        for (final String valueString : valueStrings) {
            final String[] split = valueString.split("=");
            if (split.length != 2) {
                throw new NumberFormatException("The format is invalid.");
            }

            values[switch (split[0]) {
                case "x" -> 0;
                case "y" -> 1;
                default -> throw new NumberFormatException("The provided string has a non-XY component.");
            }] = Double.parseDouble(split[1]);
        }

        return new Vector2(values);
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
                '}';
    }
}
