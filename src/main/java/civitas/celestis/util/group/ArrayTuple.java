package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.Function;

/**
 * A shallowly immutable array of objects, implemented using a primitive array.
 *
 * @param <E> The type of element to hold
 */
public class ArrayTuple<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -246136614027413248L;

    //
    // Constructors
    //

    /**
     * Creates a new array tuple.
     *
     * @param values The values this tuple should contain
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public ArrayTuple(@Nonnull E... values) {
        this.values = (E[]) Arrays.stream(values).toArray(Object[]::new);
    }

    /**
     * Creates a new tuple.
     *
     * @param t The tuple of which to copy values from
     */
    public ArrayTuple(@Nonnull Tuple<E> t) {
        this.values = t.array(); // No need to copy this array since all tuples are immutable
    }

    /**
     * Internal direct assignment constructor.
     * This cannot be made public to ensure immutability.
     *
     * @param values  The values this tuple should contain
     * @param ignored Ignored
     */
    private ArrayTuple(@Nonnull E[] values, boolean ignored) {
        this.values = values;
    }

    //
    // Variables
    //

    /**
     * The primitive array of values.
     * This must stay private to ensure the immutability of this class.
     */
    @Nonnull
    private final E[] values;

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
        return values.length;
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        for (final E value : values) {
            if (Objects.equals(value, obj)) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object containing the elements to check for containment
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
    @Nonnull
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return values[i];
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) Arrays.stream(values).toArray(Object[]::new);
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
    @SuppressWarnings("unchecked")
    public ArrayTuple<E> transform(@Nonnull Function<? super E, E> f) {
        return new ArrayTuple<>((E[]) Arrays.stream(values).map(f).toArray(Object[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function to apply to each element of this group
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> ArrayTuple<F> map(@Nonnull Function<? super E, F> f) {
        final F[] mapped = (F[]) new Object[values.length];

        for (int i = 0; i < values.length; i++) {
            mapped[i] = f.apply(values[i]);
        }

        return new ArrayTuple<>(mapped, true);
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
    public Iterator<E> iterator() {
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
    public List<E> list() {
        return Arrays.asList(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return Arrays.asList(values);
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
        if (!(obj instanceof Group<?>)) return false;

        if (obj instanceof ArrayTuple<?> at) {
            return Arrays.equals(values, at.values);
        }

        if (obj instanceof Tuple<?> t) {
            return Arrays.equals(values, t.array());
        }

        if (obj instanceof ArrayGroup<?> ag) {
            return Arrays.equals(values, ag.array());
        }

        if (obj instanceof Listable<?> l) {
            return list().equals(l.list());
        }

        return false;
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
        return Arrays.toString(values);
    }
}
