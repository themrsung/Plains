package civitas.celestis.util.tuple;

import civitas.celestis.util.array.SafeArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An array-based implementation of a tuple.
 *
 * @param <E> The type of element this tuple should hold
 * @see Tuple
 */
public class ArrayTuple<E> implements Tuple<E> {
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
     * @param elements The elements this tuple should hold
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public ArrayTuple(@Nonnull E... elements) {
        this.elements = (E[]) Arrays.stream(elements).toArray();
    }

    /**
     * Creates a new array tuple.
     *
     * @param t The tuple of which to copy elements from
     */
    @SuppressWarnings("unchecked")
    protected ArrayTuple(@Nonnull ArrayTuple<? extends E> t) {
        this.elements = (E[]) Arrays.stream(t.elements).toArray();
    }

    //
    // Variables
    //

    /**
     * The array of elements this tuple is holding.
     * It is important that this array stays private in order to ensure that
     * this class is immutable.
     */
    private final E[] elements;

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

    //
    // Retrieval
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to retrieve
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return elements[i];
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        for (final E element : elements) {
            if (Objects.equals(element, obj)) return true;
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
    public boolean containsAll(@Nonnull Iterable<?> i) {
        for (final Object o : i) {
            if (!contains(o)) return false;
        }

        return true;
    }

    //
    // Filtration
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function to handle the filtration of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public Tuple<E> filter(@Nonnull Predicate<? super E> f) {
        return new ArrayTuple<>((E[]) Arrays.stream(elements).filter(f).toArray());
    }


    //
    // Transformation
    //

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
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return new ArrayTuple<>((F[]) Arrays.stream(elements).map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (elements.length != t.size()) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        final G[] result = (G[]) new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            result[i] = f.apply(elements[i], t.get(i));
        }

        return new ArrayTuple<>(result);
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
    public Iterator<E> iterator() {
        return Arrays.stream(elements).iterator();
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
    public SafeArray<E> array() {
        return SafeArray.of(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return Arrays.asList(elements);
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
        if (!(obj instanceof Tuple<?> t)) return false;
        if (elements.length != t.size()) return false;

        for (int i = 0; i < elements.length; i++) {
            if (!Objects.equals(elements[i], t.get(i))) return false;
        }

        return true;
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
        return Arrays.toString(elements);
    }
}