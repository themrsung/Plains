package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.Function;

/**
 * A shallowly immutable triple of objects.
 *
 * @param <E> The element this triple should hold
 */
public class Triple<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -2958813754111958549L;

    //
    // Constructors
    //

    /**
     * Creates a new triple.
     *
     * @param a The first element of this triple
     * @param b The second element of this triple
     * @param c The third element of this triple
     */
    public Triple(@Nonnull E a, @Nonnull E b, @Nonnull E c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a new triple.
     *
     * @param t The tuple of which to copy values from
     * @throws IllegalArgumentException When the tuple's size is not {@code 3},
     *                                  or an unknown error occurs during variable assignment
     */
    public Triple(@Nonnull Tuple<E> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The size of the provided tuple is not 3.");
        }

        try {
            this.a = t.get(0);
            this.b = t.get(1);
            this.c = t.get(2);
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

    /**
     * The third element of this tuple.
     */
    @Nonnull
    protected final E c;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@code 3}
     */
    @Override
    public int size() {
        return 3;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj) || Objects.equals(c, obj);
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
            case 2 -> c;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 3.");
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
     * Returns the third element of this tuple.
     *
     * @return The third element of this tuple
     */
    @Nonnull
    public E getC() {
        return c;
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
        return (E[]) new Object[]{a, b, c};
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
    public Triple<E> transform(@Nonnull Function<? super E, E> f) {
        return new Triple<>(f.apply(a), f.apply(b), f.apply(c));
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
    public <F> Triple<F> map(@Nonnull Function<? super E, F> f) {
        return new Triple<>(f.apply(a), f.apply(b), f.apply(c));
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
        return List.of(a, b, c).iterator();
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
        return List.of(a, b, c);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of(a, b, c);
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

        if (obj instanceof Triple<?> t) {
            return Objects.equals(a, t.a) &&
                    Objects.equals(b, t.b) &&
                    Objects.equals(c, t.c);
        }

        if (obj instanceof Tuple<?> t) {
            if (t.size() != 3) return false;
            return Objects.equals(a, t.get(0)) &&
                    Objects.equals(b, t.get(1)) &&
                    Objects.equals(c, t.get(2));
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
        return "[" + a + ", " + b + ", " + c + "]";
    }
}
