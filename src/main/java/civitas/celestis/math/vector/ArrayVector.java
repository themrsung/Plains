package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * An n-dimensional {@code double} vector with a variable size.
 */
public class ArrayVector implements DoubleVector<ArrayVector>, Iterable<Double> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 3503198359305084452L;

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param values The component values of this vector
     */
    public ArrayVector(@Nonnull double... values) {
        this.values = Arrays.stream(values).toArray();
    }

    /**
     * Creates a new vector.
     *
     * @param g The group of which to copy component values from
     */
    public ArrayVector(@Nonnull Group<Double> g) {
        this(g.collect());
    }

    /**
     * Creates a new vector.
     *
     * @param c The collection of which to copy component values from
     */
    public ArrayVector(@Nonnull Collection<Double> c) {
        this.values = new double[c.size()];

        int i = 0;
        for (final double v : c) {
            values[i++] = v;
        }
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public ArrayVector(@Nonnull Vector<?, ?> v) {
        final Collection<? extends Number> collection = v.collect();
        this.values = new double[collection.size()];

        int i = 0;
        for (final Number n : collection) {
            values[i++] = n.doubleValue();
        }
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public ArrayVector(@Nonnull DoubleVector<?> v) {
        this.values = Arrays.stream(v.array()).toArray();
    }

    //
    // Variables
    //

    /**
     * The array of this vector's values.
     * This must remain private in order to ensure immutability.
     */
    @Nonnull
    private final double[] values;

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
        for (final double value : values) {
            if (value != 0) return false;
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
        for (final double value : values) {
            if (Double.isNaN(value)) return true;
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
        for (final double value : values) {
            if (!Double.isFinite(value)) return false;
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
        for (final double value : values) {
            if (Double.isInfinite(value)) return true;
        }

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

        for (final double value : values) {
            sumOfSquares += value * value;
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

        for (final double value : values) {
            sumOfAbsolutes += Math.abs(value);
        }

        return sumOfAbsolutes;
    }

    /**
     * Returns the dimension count of this vector. (the number of components it has)
     *
     * @return The number of components this vector has
     */
    public final int dimensions() {
        return values.length;
    }

    //
    // Getters
    //

    /**
     * Returns the {@code i}th component of this vector.
     *
     * @param i The index of component to get
     * @return The component at the specified index
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public final double get(int i) throws IndexOutOfBoundsException {
        return values[i];
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public final double[] array() {
        return Arrays.stream(values).toArray();
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
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] + s;
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector subtract(double s) {
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - s;
        }

        return new ArrayVector(result);
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
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * s;
        }

        return new ArrayVector(result);
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

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] / s;
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector divideAllowZero(double s) {
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] / s;
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to add to this vector
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector add(@Nonnull ArrayVector v) throws ArithmeticException {
        if (values.length != v.values.length) throw new ArithmeticException("Vector dimensions are different.");

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] + v.values[i];
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to subtract from this vector
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector subtract(@Nonnull ArrayVector v) throws ArithmeticException {
        if (values.length != v.values.length) throw new ArithmeticException("Vector dimensions are different.");

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - v.values[i];
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Override
    public double dot(@Nonnull ArrayVector v) throws ArithmeticException {
        double sumOfSquares = 0;

        for (final double value : values) {
            sumOfSquares += value * value;
        }

        return sumOfSquares;
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
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = -values[i];
        }

        return new ArrayVector(result);
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
            return divide(norm());
        } catch (final ArithmeticException ignored) {
            return new ArrayVector(new double[values.length]);
        }
    }

    //
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Override
    public double distance(@Nonnull ArrayVector v) throws ArithmeticException {
        return subtract(v).norm();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Override
    public double distance2(@Nonnull ArrayVector v) throws ArithmeticException {
        return subtract(v).norm2();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Override
    public double distanceManhattan(@Nonnull ArrayVector v) throws ArithmeticException {
        return subtract(v).normManhattan();
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to respect
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector min(@Nonnull ArrayVector v) throws ArithmeticException {
        if (values.length != v.values.length) throw new ArithmeticException("Vector dimensions are different.");

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = Math.min(values[i], v.values[i]);
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector to respect
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector max(@Nonnull ArrayVector v) throws ArithmeticException {
        if (values.length != v.values.length) throw new ArithmeticException("Vector dimensions are different.");

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = Math.max(values[i], v.values[i]);
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @return {@inheritDoc}
     * @throws ArithmeticException When the vectors' dimensions are different
     */
    @Nonnull
    @Override
    public ArrayVector clamp(@Nonnull ArrayVector min, @Nonnull ArrayVector max) throws ArithmeticException {
        if (values.length != min.values.length || min.values.length != max.values.length) {
            throw new ArithmeticException("Vector dimensions are different.");
        }

        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = Numbers.clamp(values[i], min.values[i], max.values[i]);
        }

        return new ArrayVector(result);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this object
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector transform(@Nonnull Function<? super Double, Double> f) {
        final double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = f.apply(values[i]);
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function to apply to each element of this object
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> Tuple<F> map(@Nonnull Function<? super Double, F> f) {
        final F[] result = (F[]) new Object[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = f.apply(values[i]);
        }

        return Tuple.of(result);
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public Iterator<Double> iterator() {
        return Arrays.stream(values).iterator();
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
        return Arrays.stream(values).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Double> collect() {
        return Arrays.stream(values).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> group() {
        return Tuple.of(Arrays.stream(values).boxed().toArray(Double[]::new));
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
        if (obj instanceof ArrayVector av) {
            return Arrays.equals(values, av.values);
        }

        if (obj instanceof DoubleVector<?> dv) {
            return Arrays.equals(values, dv.array());
        }

        if (obj instanceof BigVector<?, ?> bv) {
            final Number[] a1 = bv.array();
            if (values.length != a1.length) return false;

            for (int i = 0; i < values.length; i++) {
                if (values[i] != a1[i].doubleValue()) return false;
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
    public boolean equals(@Nullable ArrayVector v) {
        if (v == null) return false;
        return Arrays.equals(values, v.values);
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
    public static ArrayVector parseVector(@Nonnull String input) throws NumberFormatException {
        final String[] split = input.trim().replaceAll("[\\[\\]]", "").split(", ");
        final double[] values = new double[split.length];

        for (int i = 0; i < split.length; i++) {
            values[i] = Double.parseDouble(split[i]);
        }

        return new ArrayVector(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return Arrays.toString(values);
    }
}
