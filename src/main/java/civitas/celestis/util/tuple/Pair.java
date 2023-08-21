package civitas.celestis.util.tuple;

import civitas.celestis.util.array.SafeArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A fixed-size tuple which can only hold two elements.
 * Pairs use AB notation to identify their elements.
 *
 * @param <E> The type of element this tuple should hold
 * @see Tuple
 * @see BiPair
 */
public class Pair<E> implements Tuple<E> {
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
     * Creates a new {@link BiPair binary pair} with two elements of different types.
     *
     * @param a   The first element of the pair
     * @param b   The second element of the pair
     * @param <A> The type of the first element
     * @param <B> The type of the second element
     * @return The binary pair constructed from the provided elements
     */
    @Nonnull
    public static <A, B> BiPair<A, B> of(A a, B b) {
        return new BiPair<>(a, b);
    }

    //
    // Constructors
    //

    /**
     * Creates a new pair.
     *
     * @param a The first element of this pair
     * @param b The second element of this pair
     */
    public Pair(E a, E b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Creates a new pair.
     *
     * @param elements An array containing the elements of this pair in AB order
     * @throws IllegalArgumentException When the array's length is not {@code 2}
     */
    public Pair(@Nonnull E[] elements) {
        if (elements.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.a = elements[0];
        this.b = elements[1];
    }

    /**
     * Creates a new pair.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 2}
     */
    public Pair(@Nonnull Tuple<? extends E> t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The provided tuple's length is not 2.");
        }

        this.a = t.get(0);
        this.b = t.get(1);
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
        return 2;
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
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 2.");
        };
    }

    /**
     * Returns the A component (the first component) of this pair.
     *
     * @return The A component of this pair
     */
    public E a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this pair.
     *
     * @return The B component of this pair
     */
    public E b() {
        return b;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj);
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
        return new Pair<>(f.apply(a), f.apply(b));
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
        if (t.size() != 2) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Pair<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)));
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
        return SafeArray.of(a, b);
    }

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
        return "[" + a + ", " + b + "]";
    }

    //
    // Binary Pair
    //

    /**
     * A specialized pair used to hold two elements of different types.
     *
     * @param <A> The first element's type
     * @param <B> The second element's type
     * @see Pair
     */
    public static class BiPair<A, B> extends Pair<Object> {
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
         * Creates a new binary pair.
         *
         * @param a The first element of this pair
         * @param b The second element of this pair
         */
        protected BiPair(A a, B b) {
            super(a, b);
        }

        /**
         * Creates a new pair.
         *
         * @param p The pair of which to copy elements from
         */
        protected BiPair(@Nonnull BiPair<A, B> p) {
            super(p);
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
    }
}
