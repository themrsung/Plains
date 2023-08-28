package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

/**
 * A tuple with one element.
 */
public class Double1 implements DoubleTuple {
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
     * Creates a new tuple.
     *
     * @param element The element of this tuple
     */
    public Double1(double element) {
        this.element = element;
    }

    /**
     * Creates a new tuple.
     *
     * @param elements The singular array of this tuple's element
     * @throws IllegalArgumentException When the provided array's length is not {@code 1}
     */
    public Double1(@Nonnull double[] elements) {
        if (elements.length != 1) {
            throw new IllegalArgumentException("The provided array's length is not 1.");
        }

        this.element = elements[0];
    }

    /**
     * Creates a new tuple.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 1}
     */
    public Double1(@Nonnull DoubleTuple t) {
        if (t.size() != 1) {
            throw new IllegalArgumentException("The provided tuple's size is not 1.");
        }

        this.element = t.get(0);
    }

    //
    // Variables
    //

    /**
     * The element of this tuple.
     */
    protected final double element;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return 1;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return element == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(element);
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(double v) {
        return element == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Double> i) {
        for (final Double v : i) {
            if (v == null) return false;
            if (!contains(v)) return false;
        }

        return true;
    }

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public double get(int i) throws IndexOutOfBoundsException {
        if (i != 0) throw new TupleIndexOutOfBoundsException(i);
        return element;
    }

    /**
     * Returns the element of this tuple.
     *
     * @return The element of this tuple
     */
    public double get() {
        return element;
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleTuple map(@Nonnull DoubleUnaryOperator f) {
        return DoubleTuple.of(f.applyAsDouble(element));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull DoubleFunction<? extends F> f) {
        return Tuple.of(f.apply(element));
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
        return new double[]{element};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleStream stream() {
        return DoubleStream.of(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> boxed() {
        return Tuple.of(element);
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
        if (!(obj instanceof DoubleTuple t)) return false;
        if (t.size() != 1) return false;
        return Objects.equals(element, t.get(0));
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
        return "[" + element + "]";
    }
}
