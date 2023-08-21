package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A fixed-size tuple which can only hold three elements.
 * Triples use ABC notation to identity their elements.
 *
 * @param <E> The type of element this tuple should hold
 * @see Tuple
 * @see TriTriple
 */
public class Triple<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Static Initializers
    //

    /**
     * Creates a new {@link TriTriple ternary triple} with three elements of different types.
     *
     * @param a   The first element of the triple
     * @param b   The second element of the triple
     * @param c   The third element of the triple
     * @param <A> The type of the first element
     * @param <B> The type of the second element
     * @param <C> The type of the third element
     * @return The ternary triple constructed from the provided elements
     */
    @Nonnull
    public static <A, B, C> TriTriple<A, B, C> of(A a, B b, C c) {
        return new TriTriple<>(a, b, c);
    }

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
    public Triple(E a, E b, E c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a new triple.
     *
     * @param elements An array containing the elements of this triple in ABC order
     * @throws IllegalArgumentException When the array's length is not {@code 3}
     */
    public Triple(@Nonnull E[] elements) {
        if (elements.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.a = elements[0];
        this.b = elements[1];
        this.c = elements[2];
    }

    /**
     * Creates a new triple.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 3}
     */
    public Triple(@Nonnull Tuple<? extends E> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The provided tuple's size is not 3.");
        }

        this.a = t.get(0);
        this.b = t.get(1);
        this.c = t.get(2);
    }

    //
    // Variables
    //

    /**
     * The first element of this tuple.
     */
    protected final E a;

    /**
     * The second element of this tuple.
     */
    protected final E b;

    /**
     * The third element of this tuple.
     */
    protected final E c;

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
        return 3;
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
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 3.");
        };
    }

    /**
     * Returns the A component (the first component) of this triple.
     *
     * @return The A component of this triple
     */
    public E a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this triple.
     *
     * @return The B component of this triple
     */
    public E b() {
        return b;
    }

    /**
     * Returns the C component (the third component) of this triple.
     *
     * @return The C component of this triple
     */
    public E c() {
        return c;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj) || Objects.equals(c, obj);
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
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return new Triple<>(f.apply(a), f.apply(b), f.apply(c));
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
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (t.size() != 3) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Triple<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)), f.apply(c, t.get(2)));
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
        return SafeArray.of(a, b, c);
    }

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
        return array().equals(t.array());
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

    //
    // Ternary Triple
    //

    /**
     * A specialized triple used to hold three elements of different types.
     *
     * @param <A> The first element's type
     * @param <B> The second element's type
     * @param <C> The third element's type
     * @see Triple
     */
    public static class TriTriple<A, B, C> extends Triple<Object> {
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
         * Creates a new ternary triple.
         *
         * @param a The first element of this triple
         * @param b The second element of this triple
         * @param c The third element of this triple
         */
        protected TriTriple(A a, B b, C c) {
            super(a, b, c);
        }

        /**
         * Creates a new ternary triple.
         *
         * @param t The triple of which to copy elements from
         */
        protected TriTriple(@Nonnull TriTriple<A, B, C> t) {
            super(t);
        }

        //
        // Getters
        //

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public A a() {
            return (A) super.a();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public B b() {
            return (B) super.b();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public C c() {
            return (C) super.c();
        }
    }
}
