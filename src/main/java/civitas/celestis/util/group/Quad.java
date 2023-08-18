package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.Function;

/**
 * A shallowly immutable quad of objects.
 *
 * @param <E> The element this quad should hold
 */
public class Quad<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 3631284831645460610L;

    //
    // Constructors
    //

    /**
     * Creates a new quad.
     *
     * @param a The first element of this quad
     * @param b The second element of this quad
     * @param c The third element of this quad
     */
    public Quad(@Nonnull E a, @Nonnull E b, @Nonnull E c, @Nonnull E d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Creates a new quad.
     *
     * @param t The tuple of which to copy values from
     * @throws IllegalArgumentException When the tuple's size is not {@code 4},
     *                                  or an unknown error occurs during variable assignment
     */
    public Quad(@Nonnull Tuple<E> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The size of the provided tuple is not 4.");
        }

        try {
            this.a = t.get(0);
            this.b = t.get(1);
            this.c = t.get(2);
            this.d = t.get(3);
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

    /**
     * The fourth element of this tuple.
     */
    @Nonnull
    protected final E d;

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
        return 4;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj) || Objects.equals(c, obj) || Objects.equals(d, obj);
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
            case 3 -> d;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 4.");
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
     * Returns the fourth element of this tuple.
     *
     * @return The fourth element of this tuple
     */
    @Nonnull
    public E getD() {
        return d;
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
        return (E[]) new Object[]{a, b, c, d};
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
    public Quad<E> transform(@Nonnull Function<? super E, E> f) {
        return new Quad<>(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
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
    public <F> Quad<F> map(@Nonnull Function<? super E, F> f) {
        return new Quad<>(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
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
        return List.of(a, b, c, d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of(a, b, c, d);
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Group<?>)) return false;

        if (obj instanceof Quad<?> q) {
            return Objects.equals(a, q.a) &&
                    Objects.equals(b, q.b) &&
                    Objects.equals(c, q.c) &&
                    Objects.equals(d, q.d);
        }

        if (obj instanceof Tuple<?> t) {
            if (t.size() != 4) return false;
            return Objects.equals(a, t.get(0)) &&
                    Objects.equals(b, t.get(1)) &&
                    Objects.equals(c, t.get(2)) &&
                    Objects.equals(d, t.get(3));
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
        return "[" + a + ", " + b + ", " + c + ", " + d + "]";
    }
}
