package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * An array-based tuple which holds an arbitrary element of type {@code int}.
 */
public class IntArrayTuple implements IntTuple {
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
     * Creates a new array tuple.
     *
     * @param elements The elements this tuple should contain
     */
    public IntArrayTuple(@Nonnull int... elements) {
        this.elements = Arrays.stream(elements).toArray();
    }

    /**
     * Creates a new array tuple.
     *
     * @param t The tuple of which to copy elements from
     */
    public IntArrayTuple(@Nonnull IntTuple t) {
        this.elements = t.array();
    }

    //
    // Variables
    //

    /**
     * The internal array of elements. It is important that this array stays
     * private in order to ensure the immutability of this tuple.
     */
    @Nonnull
    private final int[] elements;

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
        return elements.length;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        for (final int element : elements) {
            if (element != 0) return false;
        }

        return true;
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
    public boolean contains(int v) {
        for (final int element : elements) {
            if (element == v) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Integer> i) {
        for (final Integer v : i) {
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
    public int get(int i) throws IndexOutOfBoundsException {
        try {
            return elements[i];
        } catch (final IndexOutOfBoundsException e) {
            throw new TupleIndexOutOfBoundsException(i);
        }
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple map(@Nonnull IntUnaryOperator f) {
        return IntTuple.of(stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> Tuple<F> mapToObj(@Nonnull IntFunction<? extends F> f) {
        return Tuple.of((F[]) stream().mapToObj(f).toArray());
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
    public int[] array() {
        return stream().toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntStream stream() {
        return Arrays.stream(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of(stream().boxed().toArray(Integer[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Integer> boxed() {
        return Tuple.of(stream().boxed().toArray(Integer[]::new));
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
        if (!(obj instanceof IntTuple t)) return false;
        return Arrays.equals(elements, t.array());
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
        return Arrays.toString(elements);
    }
}
