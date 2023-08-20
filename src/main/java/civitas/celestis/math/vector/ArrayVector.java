package civitas.celestis.math.vector;

import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An immutable n-dimensional mathematical vector. Numeric values are represented
 * using the primitive type {@code double}. Components have no specified notation.
 * <p>
 * Variable-size vectors are similar to tuples, but different in that they natively use
 * the {@code double} type as opposed to the boxed {@link Double} type that tuples use.
 * </p>
 *
 * @see Vector
 */
public class ArrayVector implements Vector<ArrayVector> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 6617891624709576756L;

    //
    // Constructors
    //

    /**
     * Creates a new array vector.
     *
     * @param components An array containing the components of this vector in
     *                   sequential order
     */
    public ArrayVector(@Nonnull double... components) {
        this.components = Arrays.stream(components).toArray();
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public ArrayVector(@Nonnull Vector<?> v) {
        this.components = v.array();
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public ArrayVector(@Nonnull Tuple<? extends Number> t) {
        this.components = Arrays.stream(t.array())
                .mapToDouble(Number::doubleValue)
                .toArray();
    }

    //
    // Variables
    //

    /**
     * The internal array of components.
     * It is important that this stays private in order to ensure immutability.
     */
    @Nonnull
    private final double[] components;

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
        return components.length;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        for (final double component : components) {
            if (component != 0) return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        for (final double component : components) {
            if (Double.isNaN(component)) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        for (final double component : components) {
            if (!Double.isFinite(component)) return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(norm2());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm2() {
        double sumOfSquares = 0;

        for (final double component : components) {
            sumOfSquares += component * component;
        }

        return sumOfSquares;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double normManhattan() {
        double sumOfAbsolutes = 0;

        for (final double component : components) {
            sumOfAbsolutes += Math.abs(component);
        }

        return sumOfAbsolutes;
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
        return components[i];
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
    public ArrayVector add(double s) {
        return new ArrayVector(Arrays.stream(components).map(c -> c + s).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector subtract(double s) {
        return new ArrayVector(Arrays.stream(components).map(c -> c - s).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector multiply(double s) {
        return new ArrayVector(Arrays.stream(components).map(c -> c * s).toArray());
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
    public ArrayVector divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new ArrayVector(Arrays.stream(components).map(c -> c / s).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector add(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return merge(v, Double::sum);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector subtract(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return merge(v, (a, b) -> a - b);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Override
    public double dot(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return merge(v, (a, b) -> a * b).norm2();
    }

    //
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Override
    public double distance(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return subtract(v).norm();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Override
    public double distance2(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return subtract(v).norm2();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Override
    public double distanceManhattan(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return subtract(v).normManhattan();
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector min(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return merge(v, Math::min);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to compare to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector max(@Nonnull ArrayVector v) throws IllegalArgumentException {
        return merge(v, Math::max);
    }

    /**
     * {@inheritDoc}
     *
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector clamp(@Nonnull ArrayVector min, @Nonnull ArrayVector max)
            throws IllegalArgumentException {
        return merge(min, Double::max).merge(max, Double::min);
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
    public ArrayVector negate() {
        return new ArrayVector(Arrays.stream(components).map(c -> -c).toArray());
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
    public ArrayVector normalize() throws ArithmeticException {
        return divide(norm());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector normalizeOrZero() {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return this;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector normalizeOrDefault(@Nonnull ArrayVector v) {
        try {
            return normalize();
        } catch (final ArithmeticException e) {
            return v;
        }
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
    public ArrayVector transform(@Nonnull UnaryOperator<Double> f) {
        return new ArrayVector(Arrays.stream(components).map(f::apply).toArray());
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
    @SuppressWarnings("unchecked")
    public <T> Tuple<T> map(@Nonnull Function<Double, T> f) {
        return Tuple.of((T[]) Arrays.stream(components).mapToObj(f::apply).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to merge this vector with
     * @param f The merger function to handle the merging of the two vectors
     * @return {@inheritDoc}
     * @throws IllegalArgumentException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector merge(@Nonnull ArrayVector v, @Nonnull BinaryOperator<Double> f)
            throws IllegalArgumentException {
        if (components.length != v.components.length) {
            throw new IllegalArgumentException("Vector dimensions must match for this operation.");
        }

        final double[] result = new double[components.length];

        for (int i = 0; i < components.length; i++) {
            result[i] = f.apply(components[i], v.components[i]);
        }

        return new ArrayVector(result);
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
        return Arrays.stream(components).toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> tuple() {
        return Tuple.of(Arrays.stream(components).boxed().toArray(Double[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(Arrays.stream(components).boxed().toArray(Double[]::new));
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
        if (components.length != v.dimensions()) return false;

        return Arrays.equals(components, v.array());
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable ArrayVector v) {
        return v != null && Arrays.equals(components, v.components);
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
        return Arrays.toString(components);
    }
}
