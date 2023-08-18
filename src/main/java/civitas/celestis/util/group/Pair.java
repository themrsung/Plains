package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.Function;

/**
 * A shallowly immutable pair of objects.
 *
 * @param <E> The element this pair should hold
 */
public class Pair<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 2650886773430723103L;

    //
    // Constructors
    //

    /**
     * Creates a new pair.
     *
     * @param a The first element of this pair
     * @param b The second element of this pair
     */
    public Pair(@Nonnull E a, @Nonnull E b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Creates a new pair.
     *
     * @param t The tuple of which to copy values from
     * @throws IllegalArgumentException When the tuple's size is not {@code 2},
     *                                  or an unknown error occurs during variable assignment
     */
    public Pair(@Nonnull Tuple<E> t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The size of the provided tuple is not 2.");
        }

        try {
            this.a = t.get(0);
            this.b = t.get(1);
        } catch (final Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    //
    // Variables
    //

    /**
     * The first element of this tuple.
     */
    @Nonnull
    protected final E a;

    /**
     * The second element of this tuple.
     */
    @Nonnull
    protected final E b;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@code 2}
     */
    @Override
    public int size() {
        return 2;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj);
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
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 2.");
        };
    }

    /**
     * Returns the first element of this tuple.
     *
     * @return The first element of this tuple
     */
    @Nonnull
    public E getA() {
        return a;
    }

    /**
     * Returns the second element of this tuple.
     *
     * @return The second element of this tuple
     */
    @Nonnull
    public E getB() {
        return b;
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
        return (E[]) new Object[]{a, b};
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
    public Pair<E> transform(@Nonnull Function<? super E, E> f) {
        return new Pair<>(f.apply(a), f.apply(b));
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
    public <F> Pair<F> map(@Nonnull Function<? super E, F> f) {
        return new Pair<>(f.apply(a), f.apply(b));
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
        return List.of(a, b).iterator();
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
        return List.of(a, b);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of(a, b);
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

        if (obj instanceof Pair<?> p) {
            return Objects.equals(a, p.a) &&
                    Objects.equals(b, p.b);
        }

        if (obj instanceof Tuple<?> t) {
            if (t.size() != 2) return false;
            return Objects.equals(a, t.get(0)) &&
                    Objects.equals(b, t.get(1));
        }

        if (obj instanceof ArrayGroup<?> ag) {
            return Arrays.equals(array(), ag.array());
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
        return "[" + a + ", " + b + "]";
    }
}
