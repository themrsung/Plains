package civitas.celestis.math.vector;

import civitas.celestis.math.Decimals;
import civitas.celestis.util.group.ArrayTuple;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * An n-dimensional {@link BigDecimal} vector with a variable size.
 */
public class DecimalArrayVector extends ArrayTuple<BigDecimal> implements DecimalVector<DecimalArrayVector> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -109728600478391716L;

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param values The component values of this vector
     */
    public DecimalArrayVector(@Nonnull BigDecimal... values) {
        super(values);
    }

    /**
     * Creates a new vector.
     *
     * @param g The group of which to copy component values from
     */
    public DecimalArrayVector(@Nonnull Group<BigDecimal> g) {
        super(g.collect().toArray(BigDecimal[]::new));
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public DecimalArrayVector(@Nonnull Tuple<BigDecimal> t) {
        super(t);
    }

    /**
     * Creates a new vector.
     *
     * @param c The collection of which to copy component values from
     */
    public DecimalArrayVector(@Nonnull Collection<BigDecimal> c) {
        super(c.toArray(BigDecimal[]::new));
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public DecimalArrayVector(@Nonnull Vector<?, ?> v) {
        super(v.collect().stream().map(n -> BigDecimal.valueOf(n.doubleValue())).toArray(BigDecimal[]::new));
    }

    /**
     * Creates a new vector.
     *
     * @param v The vector of which to copy component values from
     */
    public DecimalArrayVector(@Nonnull DecimalVector<?> v) {
        super(v.array());
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
    public boolean isZero() {
        for (final BigDecimal value : this) {
            if (value.equals(BigDecimal.ZERO)) return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm() {
        return norm2().sqrt(Decimals.RUNTIME_CONTEXT);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal norm2() {
        BigDecimal sumOfSquares = BigDecimal.ZERO;

        for (final BigDecimal value : this) {
            sumOfSquares = sumOfSquares.add(value.multiply(value));
        }

        return sumOfSquares;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal normManhattan() {
        BigDecimal sumOfAbsolutes = BigDecimal.ZERO;

        for (final BigDecimal value : this) {
            sumOfAbsolutes = value.abs();
        }

        return sumOfAbsolutes;
    }

    /**
     * Returns the dimension count of this vector. (the number of components it has)
     *
     * @return The number of components this vector has
     */
    public final int dimensions() {
        return size();
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
    public DecimalArrayVector add(@Nonnull BigDecimal s) {
        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).add(s);
        }

        return new DecimalArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DecimalArrayVector subtract(@Nonnull BigDecimal s) {
        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).subtract(s);
        }

        return new DecimalArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DecimalArrayVector multiply(@Nonnull BigDecimal s) {
        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).multiply(s);
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector divide(@Nonnull BigDecimal s) throws ArithmeticException {
        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).divide(s, Decimals.RUNTIME_CONTEXT);
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector add(@Nonnull DecimalArrayVector v) throws ArithmeticException {
        if (size() != v.size()) throw new ArithmeticException("Vector dimensions are different");

        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).add(v.get(i));
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector subtract(@Nonnull DecimalArrayVector v) throws ArithmeticException {
        if (size() != v.size()) throw new ArithmeticException("Vector dimensions are different");

        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).subtract(v.get(i));
        }

        return new DecimalArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal dot(@Nonnull DecimalArrayVector v) {
        if (size() != v.size()) throw new ArithmeticException("Vector dimensions are different");

        BigDecimal sumOfSquares = BigDecimal.ZERO;

        for (int i = 0; i < size(); i++) {
            sumOfSquares = sumOfSquares.add(get(i).multiply(v.get(i)));
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
    public DecimalArrayVector negate() {
        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).negate();
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector normalize() throws ArithmeticException {
        return divide(norm());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DecimalArrayVector normalizeOrZero() {
        try {
            return divide(norm());
        } catch (final ArithmeticException e) {
            final BigDecimal[] result = new BigDecimal[size()];
            Arrays.fill(result, BigDecimal.ZERO);
            return new DecimalArrayVector(result);
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
     */
    @Nonnull
    @Override
    public BigDecimal distance(@Nonnull DecimalArrayVector v) {
        return subtract(v).norm();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal distance2(@Nonnull DecimalArrayVector v) {
        return subtract(v).norm2();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public BigDecimal distanceManhattan(@Nonnull DecimalArrayVector v) {
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
    public DecimalArrayVector min(@Nonnull DecimalArrayVector v) throws ArithmeticException {
        if (size() != v.size()) throw new ArithmeticException("Vector dimensions are different.");

        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).min(v.get(i));
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector max(@Nonnull DecimalArrayVector v) throws ArithmeticException {
        if (size() != v.size()) throw new ArithmeticException("Vector dimensions are different.");

        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).max(v.get(i));
        }

        return new DecimalArrayVector(result);
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
    public DecimalArrayVector clamp(@Nonnull DecimalArrayVector min, @Nonnull DecimalArrayVector max) throws ArithmeticException {
        if (size() != min.size() || min.size() != max.size()) {
            throw new ArithmeticException("Vector dimensions are different.");
        }

        final BigDecimal[] result = new BigDecimal[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = Decimals.clamp(get(i), min.get(i), max.get(i));
        }

        return new DecimalArrayVector(result);
    }

    //
    // Type Conversion
    //

    /**
     * This is already a group, and thus there is no need for conversion.
     * This simply returns a reference to itself.
     *
     * @return {@code this}
     */
    @Nonnull
    @Override
    public DecimalArrayVector group() {
        return this;
    }

    //
    // Numeric Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector toDouble() {
        final double[] result = new double[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i).doubleValue();
        }

        return new ArrayVector(result);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The conversion function to apply to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector toDouble(@Nonnull Function<? super BigDecimal, Double> f) {
        final double[] result = new double[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = f.apply(get(i));
        }

        return new ArrayVector(result);
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
        if (obj instanceof Tuple<?> v) {
            return super.equals(v);
        }

        if (obj instanceof Vector<?, ?> v) {
            return toDouble().equals(v);
        }

        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable DecimalArrayVector v) {
        return super.equals(v);
    }
}
