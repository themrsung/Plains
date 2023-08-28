package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.ToFloatFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * An array-based tuple which holds an arbitrary element of type {@code E}.
 *
 * @param <E> The type of element this tuple should hold
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
     * @param elements The elements this tuple should contain
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
    public ArrayTuple(@Nonnull Tuple<? extends E> t) {
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
    public E get(int i) throws IndexOutOfBoundsException {
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
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return Tuple.of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleTuple mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return DoubleTuple.of(stream().mapToDouble(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatTuple mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return FloatTuple.of(stream().map(f::apply).toArray(Float[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongTuple mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return LongTuple.of(stream().mapToLong(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return IntTuple.of(stream().mapToInt(f).toArray());
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
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException {
        if (elements.length != t.size()) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        final G[] results = (G[]) new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            results[i] = f.apply(elements[i], t.get(i));
        }

        return Tuple.of(results);
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {
        for (final E element : elements) {
            a.accept(element);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        for (int i = 0; i < elements.length; i++) {
            a.accept(i, elements[i]);
        }
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
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) stream().toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Arrays.stream(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of(elements);
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
