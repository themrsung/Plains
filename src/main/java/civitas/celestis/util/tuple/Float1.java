package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.FloatFunction;
import civitas.celestis.util.function.FloatUnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A tuple with one element.
 */
public class Float1 implements FloatTuple {
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
    public Float1(float element) {
        this.element = element;
    }

    /**
     * Creates a new tuple.
     *
     * @param elements The singular array of this tuple's element
     * @throws IllegalArgumentException When the provided array's length is not {@code 1}
     */
    public Float1(@Nonnull float[] elements) {
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
    public Float1(@Nonnull FloatTuple t) {
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
    protected final float element;

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
        return Float.isNaN(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Float.isFinite(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Float.isInfinite(element);
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
    public boolean contains(float v) {
        return element == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Float> i) {
        for (final Float v : i) {
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
    public float get(int i) throws IndexOutOfBoundsException {
        if (i != 0) throw new TupleIndexOutOfBoundsException(i);
        return element;
    }

    /**
     * Returns the element of this tuple.
     *
     * @return The element of this tuple
     */
    public float get() {
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
    public FloatTuple map(@Nonnull FloatUnaryOperator f) {
        return FloatTuple.of(f.applyAsFloat(element));
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
    public <F> Tuple<F> mapToObj(@Nonnull FloatFunction<? extends F> f) {
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
    public float[] array() {
        return new float[]{element};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Float> stream() {
        return Stream.of(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Float> list() {
        return List.of(element);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Float> boxed() {
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
        if (!(obj instanceof FloatTuple t)) return false;
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
