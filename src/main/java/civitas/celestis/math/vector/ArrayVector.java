package civitas.celestis.math.vector;

import civitas.celestis.math.Scalars;
import civitas.celestis.util.tuple.DoubleArrayTuple;
import civitas.celestis.util.tuple.DoubleTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * An arbitrary dimensional array-based vector.
 *
 * @see Vector
 * @see DoubleArrayTuple
 */
public class ArrayVector extends DoubleArrayTuple implements Vector<ArrayVector> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param components The components of this vector in sequential order
     */
    public ArrayVector(@Nonnull double... components) {
        super(components);
    }

    /**
     * Creates a new vector.
     *
     * @param t The tuple of which to copy component values from
     */
    public ArrayVector(@Nonnull DoubleTuple t) {
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

        for (int i = 0; i < size(); i++) {
            sumOfSquares += Math.pow(get(i), 2);
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

        for (int i = 0; i < size(); i++) {
            sumOfAbsolutes += Math.abs(get(i));
        }

        return sumOfAbsolutes;
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
    public ArrayVector add(double s) {
        return map(v -> v + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector subtract(double s) {
        return map(v -> v - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector multiply(double s) {
        return map(v -> v * s);
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
    public ArrayVector divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return map(v -> v / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector add(@Nonnull ArrayVector v) {
        if (size() != v.size()) {
            throw new IllegalArgumentException("Vector sizes must match for this operation.");
        }

        return merge(v, Double::sum);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector subtract(@Nonnull ArrayVector v) {
        if (size() != v.size()) {
            throw new IllegalArgumentException("Vector sizes must match for this operation.");
        }

        return merge(v, (a, b) -> a - b);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public double dot(@Nonnull ArrayVector v) {
        if (size() != v.size()) {
            throw new IllegalArgumentException("Vector sizes must match for this operation.");
        }

        double sumOfSquares = 0;

        for (int i = 0; i < size(); i++) {
            sumOfSquares += get(i) * v.get(i);
        }

        return sumOfSquares;
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
     * @param v The value of which to return in case of division by zero
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
        return map(v -> -v);
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
    public ArrayVector min(@Nonnull ArrayVector v) {
        return merge(v, Double::min);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The boundary vector of which to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector max(@Nonnull ArrayVector v) {
        return merge(v, Double::max);
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
    public ArrayVector clamp(@Nonnull ArrayVector min, @Nonnull ArrayVector max) {
        final double[] result = new double[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = Scalars.clamp(get(i), min.get(i), max.get(i));
        }
        return new ArrayVector(result);
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
    public double distance(@Nonnull ArrayVector v) {
        return subtract(v).norm();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distance2(@Nonnull ArrayVector v) {
        return subtract(v).norm2();
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return {@inheritDoc}
     */
    @Override
    public double distanceManhattan(@Nonnull ArrayVector v) {
        return subtract(v).normManhattan();
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayVector map(@Nonnull DoubleUnaryOperator f) {
        return new ArrayVector(stream().map(f).toArray());
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
    public ArrayVector merge(@Nonnull ArrayVector v, @Nonnull BinaryOperator<Double> f) {
        if (size() != v.size()) {
            throw new IllegalArgumentException("Vector sizes must match for this operation.");
        }

        final double[] result = new double[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = f.apply(get(i), v.get(i));
        }

        return new ArrayVector(result);
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
    public boolean equals(@Nullable ArrayVector v) {
        return super.equals(v);
    }
}
