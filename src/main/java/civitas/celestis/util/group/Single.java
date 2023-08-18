package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.Function;

/**
 * A tuple containing one object. This acts as a shallowly immutable reference.
 *
 * @param <E> The type of object to reference
 */
public class Single<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -6335339774365194140L;

    //
    // Constructors
    //

    /**
     * Creates a new single.
     *
     * @param object The object this single should contain
     */
    public Single(@Nonnull E object) {
        this.object = object;
    }

    /*
     * No Tuple copy constructor to prevent constructor ambiguity.
     * Use the factory methods in Tuple and Group instead.
     */

    //
    // Variables
    //

    /**
     * The object this reference is pointing to.
     */
    @Nonnull
    protected final E object;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@code 1}
     */
    @Override
    public int size() {
        return 1;
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
        return Objects.equals(object, obj);
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
            if (!Objects.equals(object, o)) return false;
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
        if (i != 0) throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 1.");
        return object;
    }

    /**
     * Returns the object contained in this single.
     *
     * @return The object contained in this single
     */
    @Nonnull
    public E get() {
        return object;
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
        return (E[]) new Object[]{object};
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
    public Single<E> transform(@Nonnull Function<? super E, E> f) {
        return new Single<>(f.apply(object));
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
    public <F> Single<F> map(@Nonnull Function<? super E, F> f) {
        return new Single<>(f.apply(object));
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
        return List.of(object).iterator();
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
        return List.of(object);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of(object);
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
        if (!(obj instanceof Group<?> g)) return false;

        if (obj instanceof Single<?> s) {
            return Objects.equals(object, s.object);
        }

        if (obj instanceof Tuple<?> t) {
            if (t.size() != 1) return false;
            return Objects.equals(object, t.get(0));
        }

        if (obj instanceof ArrayGroup<?> ag) {
            return Arrays.equals(array(), ag.array());
        }

        if (obj instanceof Listable<?> l) {
            return list().equals(l);
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
        return "[" + object + "]";
    }
}
